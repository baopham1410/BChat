package com.example.appchat.Controller;

import com.example.appchat.client.ClientApp;
import com.example.appchat.client.clienthandler;
import com.example.appchat.client.service.LoginUserService;
import com.example.appchat.model.Model;
import com.example.appchat.model.User;
import com.example.appchat.utils.AlertHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;


public class ClientLoginController {
    @FXML
    private PasswordField pwPassword;
    @FXML
    private TextField txtUsername;
    private BufferedWriter clientWriter;
    private Socket socket;
private static String username;

    public static String getUsername() {
        return username;
    }

    public ClientLoginController() {

    }

    public void onClickLogin(ActionEvent actionEvent) throws SQLException, IOException { // phuong thuc dieu khien nut login
        username = txtUsername.getText();
        if (pwPassword.getText().isEmpty() || txtUsername.getText().isEmpty()) { // check null 2 o textbox
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", null, "Username or Password is blank!");
            return;
        }else{
            ChatController.setLbUsername(username);

            socket = new Socket("localhost", 45678);
            clienthandler clienthandler = new clienthandler(socket);
            String messageForm = "evaluateAccount_" + username + "_" + pwPassword.getText();
            guiusername(getUsername());
            System.out.println("[Client Log] --> " + messageForm);
            clienthandler.sendMessage(messageForm);
            clienthandler.run();
        }

    }
    public void guiusername(String username) throws IOException {
        clientWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        clientWriter.write(username);
        clientWriter.newLine();
        clientWriter.flush();
    }
}

