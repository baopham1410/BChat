create database appchat;
use appchat;

CREATE TABLE Users
(
    user_id  INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100)       NOT NULL
);

CREATE TABLE ChatHistory
(
    message_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT,
    message    TEXT,
    timestamp  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (user_id)
);

select *
from ChatHistory;

SELECT Users.username, ChatHistory.message
FROM Users
         JOIN ChatHistory ON Users.user_id = ChatHistory.user_id
ORDER BY ChatHistory.timestamp;


