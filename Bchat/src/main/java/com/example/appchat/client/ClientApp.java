package com.example.appchat.client;

import com.example.appchat.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;
import java.util.function.Consumer;

import java.io.IOException;

/**
 * JavaFX App
 */

public class ClientApp extends Application {

    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Model.getInstance().getViewfactorys().showLogin(primaryStage);
    }






    public static void main(String[] args) {
        launch();
    }
}