package com.example.appchat.client;

import com.example.appchat.model.Model;
import com.example.appchat.utils.AlertHelper;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.Socket;

public class clienthandler implements Runnable{

    private final Socket clientSocket;
    private BufferedReader clientReader;
    private BufferedWriter clientWriter;
    private boolean isClosed = false;

    public clienthandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        // Open Read/Write stream on socket server.
        this.clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.clientWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    @Override
    public void run() {
        try {
            while (!isClosed) {
                String messageResponse = clientReader.readLine();
                System.out.println("[Clienthandler Log] --> " + messageResponse);
                if (messageResponse != null) {
                    String[] messageSplit = messageResponse.split("_");
                    if (messageSplit[2].equals("success")) {
                        System.out.println(messageResponse);

                        Model.getInstance().getViewfactorys().showClient("/client/ChatFrm"); // dang nhap thanh cong thi chuyen man hinh
//                        showAlertSuccessful("Successfull!");
                        isClosed = true;
                    } else if (messageSplit[2].equals("error")) {
                        AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", null, "Username or Password is uncorrected");
                        return;
                    } else {
                        showAlertError("Error, Evaluate account failed");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            isClosed = true;
        } finally {
            close();
        }
    }

    public void sendMessage(String message){
        try{
            clientWriter.write(message);
            clientWriter.newLine();
            clientWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Sending file error in SocketManager class");
        }
    }

    public void close() {
        isClosed = true;
        try {
            if (clientReader != null) clientReader.close();
            if (clientSocket != null) clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showAlertError(String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
    }


    private void showAlertSuccessful(String successfulMoneyTransfer) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText(successfulMoneyTransfer);
            alert.showAndWait();
    }
}
