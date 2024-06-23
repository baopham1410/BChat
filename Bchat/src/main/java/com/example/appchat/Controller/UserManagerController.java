package com.example.appchat.Controller;

import com.example.appchat.model.User;
import com.example.appchat.server.service.UserService;
import com.example.appchat.utils.AlertHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserManagerController implements Initializable {
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TableView<User> tbUsers;
    @FXML
    private TableColumn<User, Integer> colUserId;
    @FXML
    private TableColumn<User, String> colUsername;
    @FXML
    private TableColumn<User, String> colPassword;

    private final UserService userService;


    public UserManagerController() {
        userService = new UserService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // phuong thuc khoi tao, khi chay se vao day dau tien
        try {
            showDataOnTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showDataOnTable() throws SQLException { // hien thi len table
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        tbUsers.setItems(userService.getAllUsers());
    }

    private void updateTable() throws SQLException { // update lai du lieu table
        tbUsers.setItems(userService.getAllUsers());
    }

    public void onClickAdd(ActionEvent actionEvent) throws SQLException { // phuong thuc dieu khien nut add
        if (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()) { // kiem tra bi bo trong
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", null, "Username or Password is blank!"); // hien thi thong bao
            return;
        }

        String usernameInput = txtUsername.getText(); // lay username dc nhap vao textfield
        String passwordInput = txtPassword.getText(); // lay password dc nhap vao textfield

        User user = new User(usernameInput, passwordInput);

        if (!userService.addNewUser(user)) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", null, "Username is existed!"); // hien thi thong bao
            return;
        }

        updateTable();
        clear();
    }

    public void onClickDelete(ActionEvent actionEvent) throws SQLException {// phuong thuc dieu khien nut delete
        User selected = tbUsers.getSelectionModel().getSelectedItem(); // lay ra doi tuong user duoc chon tu table
        if (selected == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", null, "Please select one user to delete"); // hien thi thong bao
            return;
        }

        userService.deleteUser(selected);
        updateTable();
        clear();


    }

    public void onClickUpdate(ActionEvent actionEvent) throws SQLException { // phuong thuc dieu khien nut update
        if (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()) { // kiem tra bi bo trong
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", null, "Username or Password is blank!"); // hien thi thong bao
            return;
        }

        User selected = tbUsers.getSelectionModel().getSelectedItem(); // user dc chon tu bang

        User userToUpdate = new User();
        userToUpdate.setUserId(selected.getUserId());
        userToUpdate.setUsername(txtUsername.getText());
        userToUpdate.setPassword(txtPassword.getText());

        if (!userService.updateUser(userToUpdate)) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", null, "Username is existed!"); // hien thi thong bao
            return;
        }
        updateTable();
        clear();
    }

    public void onClickRefresh(ActionEvent actionEvent) { // phuong thuc dieu khien nut refresh
        clear();

    }

    public void onSelected(MouseEvent mouseEvent) { // phuong thuc dieu khien khi nhap chuot vao bang user
        User selected = tbUsers.getSelectionModel().getSelectedItem();
        String usernameSelected = (selected != null && selected.getUsername() != null) ? selected.getUsername() : null; // kiem tra neu selected null thi gan
        txtPassword.setText("********");
        txtUsername.setText(usernameSelected);
    }

    private void clear() { // xoa cac o textbox
        txtPassword.clear();
        txtUsername.clear();
    }
}
