package com.currencymarket.service.impl;

import com.currencymarket.dto.admindto.CompanyRequestStatus;
import com.currencymarket.dto.companydto.CompanyMarketStatus;
import com.currencymarket.dto.companydto.CreateCompanyDto;
import com.currencymarket.dto.companydto.CurrentCompanyStatusDto;
import com.currencymarket.entity.Company;
import com.currencymarket.entity.Transaction;
import com.currencymarket.entity.Wallet;
import com.currencymarket.repository.CompanyDao;
import com.currencymarket.repository.TransactionDao;
import com.currencymarket.repository.WalletDao;
import com.currencymarket.repository.impl.CompanyDaoImpl;
import com.currencymarket.repository.impl.TransactionDaoImpl;
import com.currencymarket.repository.impl.WalletDaoImpl;
import com.currencymarket.service.CompanyService;

import java.time.LocalDateTime;
import java.util.List;

public class CompanyServiceImpl implements CompanyService {
    private final CompanyDao companyDao;
    private final TransactionDao transactionDao;
    private final WalletDao walletDao;

    public CompanyServiceImpl() {
        this.companyDao = new CompanyDaoImpl();
        this.transactionDao = new TransactionDaoImpl();
        this.walletDao = new WalletDaoImpl();
    }

    @Override
    public String updateCompanyStatus(CompanyRequestStatus companyRequestStatus) {
        companyDao.update(companyRequestStatus);
        Company company = companyDao.getByCompanyName(companyRequestStatus.getCompanyName());
        Wallet wallet = createWallet(company);
        walletDao.save(wallet);
        //TODO: add company assets to wallet entity
        Transaction transaction = new Transaction();
        transaction.setUserId(company.getOwnerId());
        transaction.setCompanyId(company.getCompanyId());
        transaction.setTransactionalType("BOUGHT");
        transaction.setAmount((int) company.getCounterOfStocks());
        transaction.setLocalDateTime(LocalDateTime.now());
        transaction.setPrice(company.getStockPrice());
        transactionDao.addTransaction(transaction);
        return "Статус компании обновлен!";
    }

    @Override
    public List<CompanyRequestStatus> getAllCompanies() {
        return companyDao.getAll();
    }

    @Override
    public Company createCompany(CreateCompanyDto companyDto) {
        companyDao.save(companyDto);
        return null;
    }

    @Override
    public List<CurrentCompanyStatusDto> getCompanyByClientId(int id) {
        List<Company> company = companyDao.getById(id);
        return company.stream().map(s -> {
            CurrentCompanyStatusDto currentCompanyStatusDto = new CurrentCompanyStatusDto();
            currentCompanyStatusDto.setId(s.getCompanyId());
            currentCompanyStatusDto.setStatus(s.getCompanyStatus());
            return currentCompanyStatusDto;
        }).toList();
    }

    @Override
    public List<CompanyMarketStatus> getCompanyShares() {
        List<Company> allCompany = companyDao.getAllCompany();
        return allCompany.stream()
                .filter(company -> !company.getCompanyStatus().equals("WAITING"))
                .map(s -> {
                    CompanyMarketStatus d = new CompanyMarketStatus();
                    d.setId(s.getCompanyId());
                    d.setName(s.getCompanyName());
                    d.setPrice(s.getStockPrice());
                    return d;
                }).toList();
    }

    @Override
    public List<Company> getAllConfirmedCompanies() {
        return companyDao.getAllCompanies().stream().filter(company -> company.getCompanyStatus().equals("CONFIRMED")).toList();
    }

    @Override
    public Company getCompanyByCompanyName(String companyName) {
        return companyDao.getByCompanyName(companyName);
    }

    @Override
    public List<CompanyRequestStatus> getAllWaitingCompanies() {
        return getAllCompanies().stream()
                .filter(companyRequestStatus -> companyRequestStatus.getCompanyStatus().equals("WAITING"))
                .toList();
    }


    private Wallet createWallet(Company company) {
        Wallet wallet = new Wallet();
        wallet.setUserId(company.getOwnerId());
        wallet.setCompanyName(company.getCompanyName());
        wallet.setCounterOfStocks((int) company.getCounterOfStocks());
        return wallet;
    }

}
