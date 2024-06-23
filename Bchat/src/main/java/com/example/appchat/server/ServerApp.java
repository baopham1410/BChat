package com.example.appchat.server;


import com.example.appchat.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import com.example.appchat.view.viewfactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;
import java.util.function.Consumer;

import java.io.IOException;

/**
 * JavaFX App
 */

public class ServerApp extends Application {

    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Model.getInstance().getViewfactorys().showServer(primaryStage);
    }








    public static void main(String[] args) {
        launch();
    }
}