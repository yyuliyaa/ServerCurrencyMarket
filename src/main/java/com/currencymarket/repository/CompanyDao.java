package com.currencymarket.repository;

import com.currencymarket.dto.admindto.CompanyRequestStatus;
import com.currencymarket.dto.companydto.CreateCompanyDto;
import com.currencymarket.entity.Company;

import java.util.List;

public interface CompanyDao {
    void update(CompanyRequestStatus companyRequestStatus);

    List<CompanyRequestStatus> getAll();

    List<Company> getAllCompanies();

    void save(CreateCompanyDto companyDto);

    List<Company> getById(int id);

    List<Company> getAllCompany();

    Company getByCompanyName(String companyName);

    String getCompanyNameById(int companyId);

    int getAllStocksByCompanyId(int companyId);
}
