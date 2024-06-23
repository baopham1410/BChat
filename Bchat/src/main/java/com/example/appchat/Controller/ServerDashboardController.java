package com.example.appchat.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerDashboardController implements Initializable {
    @FXML
    private ListView<String> listMenu;
    @FXML
    private AnchorPane anchorPane;

    private ObservableList<String> item = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // phuong thuc khoi tao nhung thu can thiet
        // them menu
        item.add("Quản lý server"); // them danh muc menu
        item.add("Quản lý user");

        listMenu.setItems(item);
        listMenu.getSelectionModel().selectFirst(); // mac dinh se chon tab dau
        loadContent(listMenu.getSelectionModel().getSelectedItem()); // load noi dung cua menu dau tien
    }

    private void loadContent(String selectedItem) { // load noi dung content cua menu khi chon
        String frm = switch (selectedItem) {
            case "Quản lý user" -> "/server/UserManagerFrm.fxml";
            case "Quản lý server" -> "/server/ServerStatusFrm.fxml";
            default -> "/server/ServerStatusFrm.fxml";
        };

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(frm));
            anchorPane.getChildren().clear();
            AnchorPane adminPane = loader.load();
            anchorPane.getChildren().add(adminPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectedItem(MouseEvent mouseEvent) { // su kien khi nhan vao menu
        String selectedItem = listMenu.getSelectionModel().getSelectedItem();
        if (selectedItem == null) return;
        loadContent(selectedItem);
    }
}
