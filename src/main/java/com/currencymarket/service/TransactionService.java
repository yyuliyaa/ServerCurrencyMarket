package com.currencymarket.service;

import com.currencymarket.dto.ChartDto;
import com.currencymarket.dto.clientdto.AssetDto;
import com.currencymarket.dto.transactiondto.BuySellDto;
import com.currencymarket.dto.transactiondto.HistoryDto;
import com.currencymarket.entity.Transaction;

import java.util.List;

public interface TransactionService {

    List<AssetDto> getBoughtAssets(int id);

    void addTransaction(BuySellDto buySellDto);

    List<BuySellDto> getActiveTransaction();

    String buyAssets(BuySellDto buySellDto);

    List<HistoryDto> getTransactionHistory(int company_id);

    List<Transaction> getCompletedTransaction(int id);

    List<ChartDto> getAllFinishedTransactionForCompany(int company_id);
}
