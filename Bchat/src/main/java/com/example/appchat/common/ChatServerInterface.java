package com.example.appchat.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServerInterface extends Remote {
    void sendMessage(String message) throws RemoteException;
    void registerClient(ChatClientInterface client) throws RemoteException;
    void unregisterClient(ChatClientInterface client) throws RemoteException;
}
