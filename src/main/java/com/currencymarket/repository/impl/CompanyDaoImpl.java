package com.currencymarket.repository.impl;

import com.currencymarket.command.CompanyStatus;
import com.currencymarket.db.DataBaseConnection;
import com.currencymarket.dto.admindto.CompanyRequestStatus;
import com.currencymarket.dto.companydto.CreateCompanyDto;
import com.currencymarket.entity.Company;
import com.currencymarket.repository.CompanyDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao {
    Connection connection = DataBaseConnection.getConnection();

    @Override
    public void update(CompanyRequestStatus companyRequestStatus) {
        String sql = "Update company " +
                "SET company_status = ? " +
                "where company_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, companyRequestStatus.getCompanyStatus());
            preparedStatement.setString(2, companyRequestStatus.getCompanyName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CompanyRequestStatus> getAll() {
        List<CompanyRequestStatus> companyRequestStatuses = new ArrayList<>();

        String sql = "select username, company_name, company_status\n" +
                "from company inner join client c on company.owner_id = c.user_id;";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                CompanyRequestStatus companyRequestStatus = new CompanyRequestStatus();
                companyRequestStatus.setOwner(resultSet.getString("username"));
                companyRequestStatus.setCompanyName(resultSet.getString("company_name"));
                companyRequestStatus.setCompanyStatus(resultSet.getString("company_status"));
                companyRequestStatuses.add(companyRequestStatus);
            }
            return companyRequestStatuses;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companyList = new ArrayList<>();

        String sql = "select * " +
                "from company";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Company company = new Company();
                company.setCompanyId(resultSet.getInt(1));
                company.setCompanyName(resultSet.getString(2));
                company.setCompanyInfo(resultSet.getString(3));
                company.setStockPrice(resultSet.getFloat(4));
                company.setCounterOfStocks(resultSet.getInt(5));
                company.setOwnerId(resultSet.getInt(6));
                company.setCompanyStatus(resultSet.getString(7));
                companyList.add(company);
            }
            return companyList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(CreateCompanyDto companyDto) {
        String companyInfo = companyDto.getCompanyInfo();
        int owner_id = companyDto.getOwner_id();
        String companyName = companyDto.getCompanyName();
        float stockPrice = companyDto.getStockPrice();
        float counterOfStocks = companyDto.getCounterOfStocks();
        String sql = "insert into company (company_name, company_info, stock_price, counter_of_stocks, owner_id, company_status) " +
                "values(?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, companyName);
            preparedStatement.setString(2, companyInfo);
            preparedStatement.setFloat(3, stockPrice);
            preparedStatement.setFloat(4, counterOfStocks);
            preparedStatement.setInt(5, owner_id);
            preparedStatement.setString(6, String.valueOf(CompanyStatus.WAITING));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Company> getById(int id) {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT * from company where owner_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company();
                company.setCompanyId(resultSet.getInt(1));
                company.setCompanyName(resultSet.getString(2));
                company.setCompanyInfo(resultSet.getString(3));
                company.setStockPrice(resultSet.getFloat(4));
                company.setCounterOfStocks(resultSet.getFloat(5));
                company.setOwnerId(resultSet.getInt(6));
                company.setCompanyStatus(resultSet.getString(7));
                companies.add(company);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return companies;
    }

    @Override
    public List<Company> getAllCompany() {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT * from company";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company();
                company.setCompanyId(resultSet.getInt(1));
                company.setCompanyName(resultSet.getString(2));
                company.setCompanyInfo(resultSet.getString(3));
                company.setStockPrice(resultSet.getFloat(4));
                company.setCounterOfStocks(resultSet.getFloat(5));
                company.setOwnerId(resultSet.getInt(6));
                company.setCompanyStatus(resultSet.getString(7));
                companies.add(company);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return companies;
    }

    @Override
    public Company getByCompanyName(String companyName) {
        String sql = "SELECT * from company where company_name = ? ";
        Company company = new Company();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, companyName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                company.setCompanyId(resultSet.getInt(1));
                company.setCompanyName(resultSet.getString(2));
                company.setCompanyInfo(resultSet.getString(3));
                company.setStockPrice(resultSet.getFloat(4));
                company.setCounterOfStocks(resultSet.getFloat(5));
                company.setOwnerId(resultSet.getInt(6));
                company.setCompanyStatus(resultSet.getString(7));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return company;
    }

    @Override
    public String getCompanyNameById(int companyId) {
        String sql = "SELECT company_name from company where company_id = ?";
        String name = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return name;
    }

    @Override
    public int getAllStocksByCompanyId(int companyId) {
        String sql = "SELECT counter_of_stocks from company where company_id = ?";
        int counter = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                counter = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return counter;
    }
}
