package com.example.appchat.Controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.example.appchat.common.ChatClientInterface;
import com.example.appchat.common.ChatServerInterface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ServerStatusController extends UnicastRemoteObject implements ChatServerInterface {
    @FXML
    private Label lbStatus;
    @FXML
    private Label lbNumberConnection;
    private boolean isServerRunning;

    private static final int PORT = 1099;
    private static final ArrayList<ChatClientInterface> clients = new ArrayList<>();

    public ServerStatusController() throws RemoteException {
        super();
        isServerRunning = false;
    }

    public void startServer() {
        try {
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.rebind("ChatServer", this);
            lbStatus.setText("Server đang chạy...");
            System.out.println("Server is running...");
            isServerRunning = true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        Platform.runLater(() -> {
            int numberOfConnections = clients.size();
            lbNumberConnection.setText(Integer.toString(numberOfConnections));
        });

        for (ChatClientInterface client : clients) {
            client.receiveMessage(message);
        }
    }

    @Override
    public void registerClient(ChatClientInterface client) throws RemoteException {
        clients.add(client);
    }

    @Override
    public void unregisterClient(ChatClientInterface client) throws RemoteException {
        clients.remove(client);
    }

    public void onClickStartServer(ActionEvent actionEvent) {
        lbStatus.setText("Server đang chạy...");
        if (!isServerRunning) {
            startServer();
        }
    }

    public void onClickStopServer(ActionEvent actionEvent) {
        if (isServerRunning) {
            lbStatus.setText("Server đã dừng!");
            isServerRunning = false;
        }
    }
}
