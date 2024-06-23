package com.example.appchat.server.service;

import com.example.appchat.model.User;
import com.example.appchat.utils.PasswordHashes;
import com.example.appchat.utils.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private final Repository repository = Repository.getInstance();


    public boolean addNewUser(User user) throws SQLException { // them user
        if (isExistedUser(user)) {
            return false;
        }
        String passwordHashed = PasswordHashes.hashPassword(user.getPassword()); // ma hoa password

        String sqlInsert = String.format("insert into Users(username, password) values('%s', '%s')", user.getUsername(), passwordHashed); // tao cau lenh sql

        repository.executeUpdate(sqlInsert); // thuc thi cau lenh sql
        return true;
    }

    public void deleteUser(User user) { // xoa user
        String sql = String.format("delete from Users where username = '%s'", user.getUsername());

        repository.executeUpdate(sql);
    }

    public boolean updateUser(User user) throws SQLException { // cap nha user
        // Kiểm tra nếu username đã tồn tại (nếu username của user hiện tại không phải là username mới)
        if (isExistedUser(user) && !isUsernameUnchanged(user)) {
            return false;
        }
        String passwordHashed = PasswordHashes.hashPassword(user.getPassword()); // ma hoa password

        String sql = String.format("update Users set username = '%s', password = '%s' where user_id = %d", user.getUsername(), passwordHashed, user.getUserId());

        repository.executeUpdate(sql);
        return true;
    }


    public ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList();
        String sqlSelect = "select * from Users";

        ResultSet rs = repository.executeQuery(sqlSelect); // chua toan bo bang ghi trong db

        while (rs.next()) {
            User user = new User();

            int userIdDb = rs.getInt(1);
            String usernameDb = rs.getString(2);
            String passwordDb = rs.getString(3);

            user.setUserId(userIdDb);
            user.setUsername(usernameDb);
            user.setPassword(passwordDb);

            users.add(user); // lay ra tu db roi add vao list
        }

        return users;
    }

    private boolean isExistedUser(User user) throws SQLException { // kiem tra xem user da ton tai trong db hay chua
        String sql = String.format("select count(*) from Users where username = '%s'", user.getUsername());

        ResultSet rs = repository.executeQuery(sql);

        if (rs.next()) {
            int i = rs.getInt(1);
            return i > 0;
        }

        return false;
    }

    // Phương thức kiểm tra xem username có thay đổi hay không
    private boolean isUsernameUnchanged(User user) throws SQLException {
        String sql = String.format("SELECT username FROM Users WHERE user_id = %d", user.getUserId());
        ResultSet resultSet = repository.executeQuery(sql);
        if (resultSet.next()) {
            String oldUsername = resultSet.getString("username");
            return oldUsername.equals(user.getUsername());
        }
        return false; // Trả về false nếu không tìm thấy user
    }


}
