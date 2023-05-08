package com.currencymarket.repository.impl;

import com.currencymarket.dto.chatdto.ChatDto;
import com.currencymarket.entity.Chat;
import com.currencymarket.repository.ChatDao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatDaoImpl implements ChatDao {
    @Override
    public void save(Chat chat) {
        String sql = "insert into chat (user_id, message, date) " +
                "values (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, chat.getClient_id());
            preparedStatement.setString(2, chat.getMessage());
            preparedStatement.setObject(3, chat.getLocalDateTime());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ChatDto> getAll() {
        List<ChatDto> chatDtos = new ArrayList<>();
        String sql = "select username, message, date from chat " +
                "inner join client c on chat.user_id = c.user_id";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ChatDto chat =new ChatDto();
                chat.setUsername(resultSet.getString(1));
                chat.setMessage(resultSet.getString(2));
                chat.setDate((LocalDateTime) resultSet.getObject(3));
                chatDtos.add(chat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return chatDtos;
    }
}
