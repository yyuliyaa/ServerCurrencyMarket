package com.currencymarket.repository;

import com.currencymarket.db.DataBaseConnection;
import com.currencymarket.dto.chatdto.ChatDto;
import com.currencymarket.entity.Chat;

import java.sql.Connection;
import java.util.List;

public interface ChatDao {
    Connection connection = DataBaseConnection.getConnection();

    void save(Chat chat);

    List<ChatDto> getAll();

}
