package com.example.appchat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class sever {
    private ServerSocket serverSocket;
    private boolean running = false;


    public sever(int port) throws IOException {
        serverSocket = new ServerSocket(port);

    }



    //start server
    public void start() {
        running = true;
        System.out.println("Server started...");
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                if (!running) {
                    break;
                }
                severhandler severhandler = new severhandler(clientSocket);
                severhandler.run();

            } catch (IOException e) {
                if (running) {
                    e.printStackTrace();
                } else {
                    System.out.println("Server stopped.");
                }
            }
        }
    }


    public void stop() {
        running = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
