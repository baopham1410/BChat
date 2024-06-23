package com.example.appchat.server;


import com.example.appchat.client.service.LoginUserService;
import com.example.appchat.model.User;
import com.example.appchat.utils.AlertHelper;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;


public class severhandler implements Runnable {






    private final Socket clientSocket;
    private final LoginUserService loginUserService;

    private BufferedReader serverReader;
    private BufferedWriter serverWriter;
    private boolean isClosed = false;


    public severhandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        isClosed = false;
        loginUserService = new LoginUserService();

    }

    @Override
    public void run() {
        try {
            // Open Read/Write stream on socket server.
            serverReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            serverWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            while (!isClosed) {
                String messageFromClient = serverReader.readLine();
            System.out.println("[Server Log] --> " + messageFromClient);
                if (messageFromClient == null) {
                    break;
                }
                handleClientMessage(messageFromClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
            isClosed = true;

        } finally {
            isClosed = true;
            try {
                if (serverReader != null) serverReader.close();
                if (serverWriter != null) serverWriter.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleClientMessage(String message) throws IOException {
        String[] messageSplit = message.split("_");
        if(messageSplit[0].equals("evaluateAccount")){
            String username = messageSplit[1];
            String pass = messageSplit[2];
            System.out.println("[Server Log] --> " + username +"_"+ pass);
            User userLogin = new User(username, pass);
            try {
                if (!loginUserService.checkAccount(userLogin)) { // check tai khoan dung hay sai
                    String messageForm = "evaluateAccount_" + username + "_" + "error";
                    writeMessage(messageForm);

                }else {
                    String messageForm = "evaluateAccount_" + username + "_" + "success";
                    System.out.println("Client " + username + " is active!");
                    writeMessage(messageForm);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }



        }


    }

    public void writeMessage(String message) throws IOException {
        serverWriter.write(message);
        serverWriter.newLine();
        serverWriter.flush();
    }

}

