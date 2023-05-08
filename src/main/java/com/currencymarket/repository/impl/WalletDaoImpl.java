package com.currencymarket.repository.impl;

import com.currencymarket.entity.Wallet;
import com.currencymarket.repository.WalletDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WalletDaoImpl implements WalletDao {
    @Override
    public boolean isUpdate() {
        return false;
    }

    @Override
    public void save(Wallet wallet) {
        String sql = "insert into wallet (user_id, currency_type, amount) " +
                "values (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, wallet.getUserId());
            preparedStatement.setString(2, wallet.getCompanyName());
            preparedStatement.setInt(3, wallet.getCounterOfStocks());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getAmountByUserIdAndCompanyName(int userId, String companyName) {
        String sql = "select amount from wallet where user_id = ? and currency_type = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, companyName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public List<Wallet> getById(int userId) {
        List<Wallet> walletList = new ArrayList<>();
        String sql = "select * from wallet where user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Wallet wallet = new Wallet();
                wallet.setWalletId(resultSet.getInt(1));
                wallet.setUserId(resultSet.getInt(2));
                wallet.setCompanyName(resultSet.getString(3));
                wallet.setCounterOfStocks(resultSet.getInt(4));
                walletList.add(wallet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return walletList;
    }

    @Override
    public void update(Optional<Wallet> wallet) {
        String sql = "UPDATE wallet SET " +
                "user_id = ? ," +
                "currency_type = ?, " +
                "amount = ? " +
                "where wallet_id =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, wallet.get().getUserId());
            preparedStatement.setString(2, wallet.get().getCompanyName());
            preparedStatement.setDouble(3, wallet.get().getCounterOfStocks());
            preparedStatement.setInt(4, wallet.get().getWalletId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Wallet save(int id, String companyName) {

        String sql = "INSERT INTO wallet (user_id, currency_type) " +
                "values (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, companyName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return getWalletByUserIdAndCompanyName(id, companyName);
    }

    @Override
    public Wallet getWalletByUserIdAndCompanyName(int id, String companyName) {
        Wallet wallet = new Wallet();
        String sql = "SELECT * from wallet " +
                "where user_id = ? and currency_type = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, companyName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                wallet.setWalletId(resultSet.getInt(1));
                wallet.setUserId(resultSet.getInt(2));
                wallet.setCompanyName(resultSet.getString(3));
                wallet.setCounterOfStocks(resultSet.getInt(4));
                return wallet;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
