package com.currencymarket.repository;

import com.currencymarket.command.Status;
import com.currencymarket.db.DataBaseConnection;
import com.currencymarket.dto.clientdto.HomePageDto;
import com.currencymarket.dto.SignUpDto;

import java.sql.Connection;

public interface RegistrationDao {
    Connection connection = DataBaseConnection.getConnection();

    HomePageDto sign_up(SignUpDto signUpDto);

    Status userIsExist(String login);

    HomePageDto sign_in(SignUpDto signUpDto);

    HomePageDto checkUser(SignUpDto signUpDto);
}
