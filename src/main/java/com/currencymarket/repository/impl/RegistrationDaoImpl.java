package com.currencymarket.repository.impl;

import com.currencymarket.command.Status;
import com.currencymarket.dto.SignUpDto;
import com.currencymarket.dto.clientdto.HomePageDto;
import com.currencymarket.repository.RegistrationDao;
import com.currencymarket.utils.PasswordEncoder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDaoImpl implements RegistrationDao {


    private final String SELECT = "SELECT username FROM client";
    private HomePageDto homePageDto = null;

    public RegistrationDaoImpl() {
    }

    @Override
    public HomePageDto sign_up(SignUpDto signUpDto) {
        String password = String.valueOf(PasswordEncoder.encode(signUpDto.getPassword()));
        String username = signUpDto.getUsername();
        String sqlRequest = "INSERT INTO client (username, password ) " +
                "VALUES(?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        homePageDto = new HomePageDto();
        homePageDto.setCash(0);
        homePageDto.setUsername(username);
        homePageDto.setRole("USER");

        return homePageDto;
    }

    @Override
    public Status userIsExist(String login) {
        String sql = "SELECT * FROM client " +
                "WHERE username = \'" + login + "\'";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (resultSet.getString("username").equals(login)) {
                    return Status.USER_ALREADY_EXIST;
                }
            }
            return Status.USER_NOT_EXIST;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HomePageDto sign_in(SignUpDto signUpDto) {
        String password = PasswordEncoder.encode(signUpDto.getPassword());
        String login = signUpDto.getUsername();
        homePageDto = new HomePageDto();

        String sql = "SELECT * FROM client " +
                "WHERE username = \'" + login + "\' and password = \'" + password + "\'";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (resultSet.getString("username").equals(login) && resultSet.getString("password").equals(password)) {
                    homePageDto.setUsername(resultSet.getString("username"));
                    homePageDto.setCash(resultSet.getDouble("cash"));
                    homePageDto.setRole(resultSet.getString("role"));
                    homePageDto.setActivated(resultSet.getString("activated"));
                    homePageDto.setId(resultSet.getInt("user_id"));
                    break;
                }
            }
            return homePageDto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HomePageDto checkUser(SignUpDto signUpDto) {
        String password = PasswordEncoder.encode(signUpDto.getPassword());
        String login = signUpDto.getUsername();
        homePageDto = new HomePageDto();

        String sql = "SELECT * FROM client " +
                "WHERE username = \'" + login + "\' and password = \'" + password + "\'";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (resultSet.getString("username").equals(login) && resultSet.getString("password").equals(password)) {
                    homePageDto.setUsername(resultSet.getString("username"));
                    homePageDto.setCash(resultSet.getDouble("cash"));
                    homePageDto.setRole(resultSet.getString("role"));
                    return homePageDto;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
