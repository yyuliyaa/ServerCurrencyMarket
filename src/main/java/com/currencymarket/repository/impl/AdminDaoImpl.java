package com.currencymarket.repository.impl;

import com.currencymarket.dto.admindto.ClientInformationDto;
import com.currencymarket.repository.AdminDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class AdminDaoImpl implements AdminDao {

    @Override
    public List<ClientInformationDto> getAll() {
        List<ClientInformationDto> clientInformationDtos = new ArrayList<>();

        String sql = "SELECT user_id ,username,  role, activated " +
                "from client";
        try {
            Statement preparedStatement = connection.createStatement();
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                ClientInformationDto clientInformationDto = new ClientInformationDto();
                clientInformationDto.setUsername(resultSet.getString(2));
                clientInformationDto.setRole(resultSet.getString(3));
                clientInformationDto.setActivated((resultSet.getString(4)));
                clientInformationDto.setId(resultSet.getInt(1));
                clientInformationDtos.add(clientInformationDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientInformationDtos;
    }

    @Override
    public String deleteById(int id) {
        String sql = "update client " +
                "set activated = 'BAN'" +
                " where user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Пользователь успешно удален";
    }
}
