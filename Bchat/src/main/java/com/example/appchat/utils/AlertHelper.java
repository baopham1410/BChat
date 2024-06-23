package com.example.appchat.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertHelper { // viet 1 lan va su dung lai
    public static void showAlert(Alert.AlertType type, String title, String header, String content) { // hien thi thong bao
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }



}
