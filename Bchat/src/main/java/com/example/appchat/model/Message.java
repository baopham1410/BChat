package com.example.appchat.model;

import java.time.LocalDateTime;

public class Message {
    private int messageId;
    private int userId;
    private String message;
    private LocalDateTime timestamp;

    public Message() {
    }

    public Message(int messageId, int userId, String message, LocalDateTime timestamp) {
        this.messageId = messageId;
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
