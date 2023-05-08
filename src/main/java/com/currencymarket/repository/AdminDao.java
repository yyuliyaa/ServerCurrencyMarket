package com.currencymarket.repository;

import com.currencymarket.db.DataBaseConnection;
import com.currencymarket.dto.admindto.ClientInformationDto;

import java.sql.Connection;
import java.util.List;

public interface AdminDao {
    Connection connection = DataBaseConnection.getConnection();

    List<ClientInformationDto> getAll();

    String deleteById(int id);
}
