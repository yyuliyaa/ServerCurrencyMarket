package com.currencymarket.repository;

import com.currencymarket.db.DataBaseConnection;
import com.currencymarket.dto.admindto.ClientInformationDto;
import com.currencymarket.dto.clientdto.PersonalInfoDto;
import com.currencymarket.entity.Client;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface ClientDao {

    Connection connection = DataBaseConnection.getConnection();

    List<PersonalInfoDto> getClientInfo(int id);

    void update(ClientInformationDto clientInformationDto);

    Optional<Client> getById(int id);

    void save(Optional<Client> client);

    void updateClient(Optional<Client> client);

    Optional<Client> getByUsername(String username);
}
