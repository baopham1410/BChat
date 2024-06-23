package com.example.appchat.client.service;

import com.example.appchat.model.User;
import com.example.appchat.utils.PasswordHashes;
import com.example.appchat.utils.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginUserService {
    private Repository repository = Repository.getInstance();

    public boolean checkAccount(User user) throws SQLException {
        String passwordHashed = PasswordHashes.hashPassword(user.getPassword());

        String sql = String.format("select count(*) from Users where username='%s' and password='%s'", user.getUsername(), passwordHashed);

        ResultSet rs = repository.executeQuery(sql);

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

        return false;
    }

}
