package com.currencymarket.repository.impl;

import com.currencymarket.dto.admindto.ClientInformationDto;
import com.currencymarket.dto.clientdto.PersonalInfoDto;
import com.currencymarket.entity.Client;
import com.currencymarket.repository.ClientDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDaoImpl implements ClientDao {

    @Override
    public List<PersonalInfoDto> getClientInfo(int id) {
        List<PersonalInfoDto> personalInfoList = new ArrayList<>();

        String sql = "select username, cash, role, status_type,counter_of_stocks, company_name\n" +
                "from client\n" +
                "         inner join transaction t on client.user_id = t.user_id\n" +
                "         inner join company c on t.company_id = c.company_id\n" +
                "where t.user_id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PersonalInfoDto personalInfoDto = new PersonalInfoDto();
                personalInfoDto.setUsername(resultSet.getString(1));
                personalInfoDto.setCash(resultSet.getDouble(2));
                personalInfoDto.setRole(resultSet.getString(3));
                personalInfoDto.setStatus_type(resultSet.getString(4));
                personalInfoDto.setCounterOfStocks(resultSet.getInt(5));
                personalInfoDto.setCompanyName(resultSet.getString(6));
                personalInfoList.add(personalInfoDto);
            }
            return personalInfoList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(ClientInformationDto clientInformationDto) {
        System.out.println(clientInformationDto);
        String sql = "UPDATE client SET " +
                "activated = ? ," +
                "role = ? " +
                "where user_id =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, clientInformationDto.getActivated());
            preparedStatement.setString(2, clientInformationDto.getRole());
            preparedStatement.setInt(3, clientInformationDto.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateClient(Optional<Client> client) {
        String sql = "UPDATE client SET " +
                "username = ? ," +
                "role = ?, " +
                "cash = ?," +
                "status_type = ?, " +
                "password = ?" +
                "where user_id =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, client.get().getUsername());
            preparedStatement.setString(2, client.get().getRole());
            preparedStatement.setDouble(3, client.get().getCash());
            preparedStatement.setString(4, "ACTIVE");
            preparedStatement.setString(5, client.get().getPassword());
            preparedStatement.setInt(6, client.get().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Client> getByUsername(String username) {
        String sql = "SELECT  * from client where username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt(1));
                client.setUsername(username);
                client.setActive(resultSet.getString(4));
                client.setCash(resultSet.getDouble(5));
                client.setRole(resultSet.getString(6));
                return Optional.of(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Client> getById(int id) {
        String sql = "SELECT * from client WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Client(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5),
                        resultSet.getString(6)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void save(Optional<Client> client) {
        String sql = "insert into client" +
                " (user_id, username, password, activated, cash, role, status_type, company_id)" +
                " VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, client.get().getId());
            preparedStatement.setString(2, client.get().getUsername());
            preparedStatement.setString(3, client.get().getPassword());
            preparedStatement.setString(4, "ACTIVE");
            preparedStatement.setDouble(5, client.get().getCash());
            preparedStatement.setString(6, client.get().getRole());
            preparedStatement.setString(7, "ACTIVE");
            preparedStatement.setString(8, null);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
