package com.currencymarket.repository;

import com.currencymarket.db.DataBaseConnection;
import com.currencymarket.dto.carddto.CardDto;

import java.sql.Connection;

public interface CardDao {
    Connection connection = DataBaseConnection.getConnection();

    void save(CardDto cardDto);
    CardDto getByUserId(int userId);
}
