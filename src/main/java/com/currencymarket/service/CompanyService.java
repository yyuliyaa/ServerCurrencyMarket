package com.currencymarket.service;

import com.currencymarket.dto.companydto.CompanyMarketStatus;
import com.currencymarket.dto.companydto.CreateCompanyDto;
import com.currencymarket.dto.companydto.CurrentCompanyStatusDto;
import com.currencymarket.dto.admindto.CompanyRequestStatus;
import com.currencymarket.entity.Company;

import java.util.List;

public interface CompanyService {
    String updateCompanyStatus(CompanyRequestStatus companyRequestStatus);

    List<CompanyRequestStatus> getAllCompanies();

    Company createCompany(CreateCompanyDto companyDto);

    List<CurrentCompanyStatusDto> getCompanyByClientId(int id);

    List<CompanyMarketStatus> getCompanyShares();

    List<Company> getAllConfirmedCompanies();

    Company getCompanyByCompanyName(String companyName);

    List<CompanyRequestStatus> getAllWaitingCompanies();
}
