package com.example.appchat.Controller;


import com.example.appchat.client.service.ChatService;
import com.example.appchat.common.ChatClientInterface;
import com.example.appchat.common.ChatServerInterface;
import com.example.appchat.model.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class ChatController extends UnicastRemoteObject implements ChatClientInterface, Initializable {
    @FXML
    private Label lbUsername;
    @FXML
    private TextArea txtBoxMessage;
    @FXML
    private TextField txtInput;

    private static String username;
    private ChatServerInterface server;

    private final ChatService chatService;
    public static void setLbUsername(String name) {
        username = name;
    }

    public ChatController() throws RemoteException {
        chatService = new ChatService();
    }

//Phương thức initialize() được gọi khi controller được khởi tạo. Nó thiết lập tên người dùng và kết nối với máy chủ RMI, sau đó khởi tạo lịch sử tin nhắn.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbUsername.setText(username);
        try {
            server = (ChatServerInterface) Naming.lookup("rmi://localhost:1099/ChatServer");
            server.registerClient(this);
            initMessageHistory();
        } catch (RemoteException | NotBoundException | MalformedURLException | SQLException e) {
            e.printStackTrace();
        }
    }
//Phương thức receiveMessage() được gọi khi có tin nhắn mới. Nó sẽ hiển thị tin nhắn trên txtBoxMessage.
    @Override
    public void receiveMessage(String message) throws RemoteException {
        Platform.runLater(() -> txtBoxMessage.appendText(message + "\n"));
    }
//Phương thức receiveMessage() được gọi khi có tin nhắn mới. Nó sẽ hiển thị tin nhắn trên txtBoxMessage.
    public void onClickSend(ActionEvent actionEvent) {
        String messageInput = txtInput.getText();
        String message = String.format("%s: %s", username, messageInput);
        if (!message.isEmpty()) {
            try {
                server.sendMessage(message);

                Message saveMessage = new Message();
                saveMessage.setMessage(messageInput);
                saveMessage.setTimestamp(LocalDateTime.now());
                chatService.saveChatHistory(saveMessage, username);
                txtInput.clear();


            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
//Phương thức receiveMessage() được gọi khi có tin nhắn mới. Nó sẽ hiển thị tin nhắn trên txtBoxMessage.
    private void initMessageHistory() throws SQLException {
        List<String> messageList = chatService.getHistoryMessage();
        messageList.forEach(message -> txtBoxMessage.appendText(message + "\n"));
    }
}
