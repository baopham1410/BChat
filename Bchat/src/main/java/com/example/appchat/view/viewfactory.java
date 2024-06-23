package com.example.appchat.view;

import com.example.appchat.client.ClientApp;
import com.example.appchat.server.ServerApp;
import com.example.appchat.server.sever;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;



public class viewfactory {

    private Stage stageServer;
    private Stage stageClient;
    public  void showServer(Stage primaryStage) throws IOException {
        stageServer = primaryStage;

        Parent root = loadFXML("/server/ServerDashboardFrm");
        Scene scene = new Scene(root);
        stageServer.setScene(scene);
        stageServer.initStyle(StageStyle.DECORATED);
        stageServer.setResizable(false);
        stageServer.setTitle("Server");
        stageServer.show();

        sever sever = new sever(45678);
        new Thread(() -> sever.start()).start();
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public void showLogin(Stage primaryStage) throws IOException {

        stageClient = primaryStage;

        Parent root = loadFXML("/client/ClientLoginFrm");
        Scene scene = new Scene(root);
        stageClient.setScene(scene);
        stageClient.initStyle(StageStyle.DECORATED);
        stageClient.setResizable(true);
        stageClient.setTitle("Client");
        stageClient.show();
    }
    public void showClient(String fxml) throws IOException {
        new Thread(() -> {
            Platform.runLater(() -> {
                Stage stage = new Stage();
                Parent root = null;
                try {
                    root = loadFXMLClient(fxml);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(root);


                stage.initStyle(StageStyle.DECORATED);
                stage.setScene(scene);
                stage.setResizable(true);
                stage.setTitle("Client");
                stage.show();
                stageClient.close();
            });
        }).start();

    }

    private static Parent loadFXMLClient(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
