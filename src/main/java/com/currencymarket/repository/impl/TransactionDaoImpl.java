package com.currencymarket.repository.impl;

import com.currencymarket.entity.Transaction;
import com.currencymarket.repository.TransactionDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {

    @Override
    public void addTransaction(Transaction transaction) {
        String sql = "insert into transaction (transactional_type, user_id, company_id, amount, date, price) " +
                "values (?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, transaction.getTransactionalType());
            preparedStatement.setInt(2, transaction.getUserId());
            preparedStatement.setInt(3, transaction.getCompanyId());
            preparedStatement.setInt(4, transaction.getAmount());
            preparedStatement.setObject(5, transaction.getLocalDateTime());
            preparedStatement.setFloat(6,   transaction.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> getAllById(int id) {
        List<Transaction> transactionList = new ArrayList<>();
        String sql = "SELECT * from transaction where user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(resultSet.getInt(1));
                transaction.setTransactionalType(resultSet.getString(2));
                transaction.setUserId(resultSet.getInt(3));
                transaction.setCompanyId(resultSet.getInt(4));
                transaction.setAmount(resultSet.getInt(5));
                transaction.setLocalDateTime((LocalDateTime) resultSet.getObject(6));
                transaction.setPrice(resultSet.getFloat(7));
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactionList;
    }

    @Override
    public List<Transaction> getAll() {
        List<Transaction> transactionList = new ArrayList<>();
        String sql = "SELECT * from transaction ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(resultSet.getInt(1));
                transaction.setTransactionalType(resultSet.getString(2));
                transaction.setUserId(resultSet.getInt(3));
                transaction.setCompanyId(resultSet.getInt(4));
                transaction.setAmount(resultSet.getInt(5));
                transaction.setLocalDateTime((LocalDateTime) resultSet.getObject(6));
                transaction.setPrice(resultSet.getFloat(7));
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactionList;
    }

    @Override
    public Transaction getById(int id) {
        String sql = "SELECT * from transaction where transaction_id = ?";
        Transaction transaction = new Transaction();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                transaction.setTransactionId(resultSet.getInt(1));
                transaction.setTransactionalType(resultSet.getString(2));
                transaction.setUserId(resultSet.getInt(3));
                transaction.setCompanyId(resultSet.getInt(4));
                transaction.setAmount(resultSet.getInt(5));
                transaction.setLocalDateTime((LocalDateTime) resultSet.getObject(6));
                transaction.setPrice(resultSet.getFloat(7));
                return transaction;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void save(Transaction transaction) {
        String sql = "insert into transaction( transactional_type, user_id, company_id, amount, date, price) " +
                "values (?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,transaction.getTransactionalType());
            preparedStatement.setInt(2,transaction.getUserId());
            preparedStatement.setInt(3, transaction.getCompanyId());
            preparedStatement.setInt(4,transaction.getAmount());
            preparedStatement.setObject(5, transaction.getLocalDateTime());
            preparedStatement.setFloat(6, transaction.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Transaction transaction) {
        String sql = "update transaction " +
                "set transactional_type = ?, " +
                "amount = ?," +
                "date = ? " +
                "where transaction_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, transaction.getTransactionalType());
            preparedStatement.setInt(2, transaction.getAmount());
            preparedStatement.setObject(3, LocalDateTime.now());
            preparedStatement.setInt(4, transaction.getTransactionId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> getAllByCompanyId(int company_id) {
        List<Transaction> transactionList = new ArrayList<>();
        String sql = "SELECT * from transaction where company_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, company_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(resultSet.getInt(1));
                transaction.setTransactionalType(resultSet.getString(2));
                transaction.setUserId(resultSet.getInt(3));
                transaction.setCompanyId(resultSet.getInt(4));
                transaction.setAmount(resultSet.getInt(5));
                transaction.setLocalDateTime((LocalDateTime) resultSet.getObject(6));
                transaction.setPrice(resultSet.getFloat(7));
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactionList;
    }

    @Override
    public List<Object> get() {
        String sql = "SELECT user_id, company_id, " +
                "(SUM(CASE WHEN transactional_type='BOUGHT' THEN amount ELSE 0 END) " +
                "- SUM(CASE WHEN transactional_type='SELLED' THEN amount ELSE 0 END)) AS difference " +
                "FROM transaction " +
                "GROUP BY user_id, company_id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                int companyId = rs.getInt("company_id");
                int difference = rs.getInt("difference");

                System.out.println("User ID: " + userId + ", Company ID: " + companyId + ", Difference: " + difference);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




        return null;
    }
}
