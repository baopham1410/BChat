package com.example.appchat.client.service;


import com.example.appchat.model.Message;
import com.example.appchat.utils.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatService {
    private final Repository repository = Repository.getInstance();

    public void saveChatHistory(Message message, String username) throws SQLException { // luu lich su chat
        String sqlGetUserId = String.format("select user_id from Users where username = '%s'", username);
        int userId = 0;
        ResultSet rs = repository.executeQuery(sqlGetUserId);
        if (rs.next()) {
            userId = rs.getInt(1);
        }

        message.setUserId(userId);

        String sqlInsert = String.format("insert into ChatHistory(user_id, message, timestamp) values(%d, '%s', '%s')", message.getUserId(), message.getMessage(), message.getTimestamp());
        System.out.println(sqlInsert);

        repository.executeUpdate(sqlInsert);
    }


    public List<String> getHistoryMessage() throws SQLException { // lay lich su chat
        List<String> result = new ArrayList<>();

        String sql = """
                SELECT Users.username, ChatHistory.message
                FROM Users
                         JOIN ChatHistory ON Users.user_id = ChatHistory.user_id
                ORDER BY ChatHistory.timestamp""";

        ResultSet rs = repository.executeQuery(sql);

        while(rs.next()){
            String username = rs.getString(1);
            String message = rs.getString(2);

            result.add(String.format("%s: %s", username, message));
        }
        return result;
    }

    public static void main(String[] args) throws SQLException {
        ChatService chatService = new ChatService();
        Message message = new Message();

        message.setMessage("xin chao2");
        message.setTimestamp(LocalDateTime.now());
        chatService.saveChatHistory(message, "thinh");

        System.out.println(chatService.getHistoryMessage().toString());
    }
}
