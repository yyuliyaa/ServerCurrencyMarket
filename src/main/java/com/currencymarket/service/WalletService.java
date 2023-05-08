package com.currencymarket.service;

import com.currencymarket.entity.Wallet;

import java.util.List;

public interface WalletService {
    boolean isUpdate();


    List<Wallet> getAllUserWallet(int userId);
}
