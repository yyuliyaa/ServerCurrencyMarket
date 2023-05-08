package com.currencymarket.repository;

import com.currencymarket.db.DataBaseConnection;
import com.currencymarket.entity.Transaction;

import java.sql.Connection;
import java.util.List;

public interface TransactionDao {
    Connection connection = DataBaseConnection.getConnection();

    void addTransaction(Transaction transaction);

    List<Transaction> getAllById(int id);

    List<Transaction> getAll();

    Transaction getById(int id);

    void save(Transaction transaction);

    void update(Transaction transaction);

    List<Transaction> getAllByCompanyId(int company_id);

    List<Object> get();
}
