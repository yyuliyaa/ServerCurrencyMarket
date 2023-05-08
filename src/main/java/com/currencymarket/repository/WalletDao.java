package com.currencymarket.repository;

import com.currencymarket.db.DataBaseConnection;
import com.currencymarket.entity.Wallet;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface WalletDao {
    Connection connection = DataBaseConnection.getConnection();

    boolean isUpdate();

    void save(Wallet wallet);

    int getAmountByUserIdAndCompanyName(int userId, String companyName);

    List<Wallet> getById(int userId);

    void update(Optional<Wallet> wallet);


    Wallet save(int id, String companyName);

    Wallet getWalletByUserIdAndCompanyName(int id, String companyName);
}
