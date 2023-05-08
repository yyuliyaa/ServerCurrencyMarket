package com.currencymarket.repository.impl;

import com.currencymarket.dto.carddto.CardDto;
import com.currencymarket.repository.CardDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardDaoImpl implements CardDao {
    @Override
    public void save(CardDto cardDto) {
        String sql = "insert into card (card_cash, user_id, cv, card_number) VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setFloat(1, cardDto.getCash());
            preparedStatement.setInt(2, cardDto.getUserId());
            preparedStatement.setInt(3, cardDto.getCv());
            preparedStatement.setString(4, cardDto.getNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CardDto getByUserId(int userId) {
        String sql = "SELECT * FROM card " +
                "WHERE user_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                CardDto cardDto = new CardDto();
                cardDto.setCash(resultSet.getFloat(2));
                cardDto.setUserId(resultSet.getInt(3));
                cardDto.setCv(resultSet.getInt(4));
                cardDto.setNumber(resultSet.getString(5));
                return cardDto;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
