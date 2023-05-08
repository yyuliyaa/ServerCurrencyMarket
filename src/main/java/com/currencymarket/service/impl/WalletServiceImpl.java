package com.currencymarket.service.impl;

import com.currencymarket.entity.Wallet;
import com.currencymarket.repository.WalletDao;
import com.currencymarket.repository.impl.WalletDaoImpl;
import com.currencymarket.service.WalletService;

import java.util.List;

public class WalletServiceImpl implements WalletService {
    private final WalletDao walletDao;

    public WalletServiceImpl() {
        walletDao = new WalletDaoImpl();
    }

    @Override
    public boolean isUpdate() {
        return false;
    }

    @Override
    public List<Wallet> getAllUserWallet(int userId) {
        return walletDao.getById(userId);
    }


}
