package com.currencymarket.service.impl;

import com.currencymarket.dto.ChartDto;
import com.currencymarket.dto.clientdto.AssetDto;
import com.currencymarket.dto.transactiondto.BuySellDto;
import com.currencymarket.dto.transactiondto.HistoryDto;
import com.currencymarket.entity.Client;
import com.currencymarket.entity.Transaction;
import com.currencymarket.entity.Wallet;
import com.currencymarket.repository.ClientDao;
import com.currencymarket.repository.CompanyDao;
import com.currencymarket.repository.TransactionDao;
import com.currencymarket.repository.WalletDao;
import com.currencymarket.repository.impl.ClientDaoImpl;
import com.currencymarket.repository.impl.CompanyDaoImpl;
import com.currencymarket.repository.impl.TransactionDaoImpl;
import com.currencymarket.repository.impl.WalletDaoImpl;
import com.currencymarket.service.TransactionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionDao transactionDao;
    private final CompanyDao companyDao;
    private final ClientDao clientDao;
    private final WalletDao walletDao;

    public TransactionServiceImpl() {
        transactionDao = new TransactionDaoImpl();
        companyDao = new CompanyDaoImpl();
        clientDao = new ClientDaoImpl();
        walletDao = new WalletDaoImpl();
    }

    @Override
    public List<AssetDto> getBoughtAssets(int id) {
        return transactionDao.getAllById(id).stream()
                .filter(transaction -> transaction.getTransactionalType().equals("BOUGHT"))
                .map((transaction) -> {
                    AssetDto assetDto = new AssetDto();
                    assetDto.setCompanyName(companyDao.getCompanyNameById(transaction.getCompanyId()));
                    assetDto.setCounterOfStocks(transaction.getAmount());
                    assetDto.setPercentOfCounterStock(companyDao.getAllStocksByCompanyId(transaction.getCompanyId()) / transaction.getAmount() * 100);
                    return assetDto;
                }).toList();

    }

    @Override
    public void addTransaction(BuySellDto buySellDto) {
        Wallet wallet = walletDao.getById(buySellDto.getUserId()).stream()
                .filter(w -> w.getCompanyName().equals(buySellDto.getCompanyName()))
                .findFirst().get();

        Transaction transaction = new Transaction();
        transaction.setTransactionalType(buySellDto.getStatus());
        transaction.setLocalDateTime(LocalDateTime.now());

        transaction.setAmount(buySellDto.getCounterOfStocks());
        transaction.setUserId(buySellDto.getUserId());
        transaction.setCompanyId(companyDao.getByCompanyName(buySellDto.getCompanyName()).getCompanyId());
        transaction.setPrice(buySellDto.getPrice());
        transactionDao.addTransaction(transaction);

        wallet.setCounterOfStocks(wallet.getCounterOfStocks() - buySellDto.getCounterOfStocks());
        walletDao.update(Optional.of(wallet));
    }

    @Override
    public List<BuySellDto> getActiveTransaction() {
        return transactionDao.getAll().stream()
                .filter(transaction -> transaction.getTransactionalType().equals("BUY") || transaction.getTransactionalType().equals("SELL"))
                .map(transaction -> {
                    BuySellDto buySellDto = new BuySellDto();
                    buySellDto.setUserId(transaction.getUserId());
                    buySellDto.setTransaction_id(transaction.getTransactionId());
                    buySellDto.setCompanyName(companyDao.getCompanyNameById(transaction.getCompanyId()));
                    buySellDto.setCounterOfStocks(transaction.getAmount());
                    buySellDto.setStatus(transaction.getTransactionalType());
                    buySellDto.setPrice(transaction.getPrice());
                    return buySellDto;
                })
                .toList();
    }

    @Override
    public String buyAssets(BuySellDto buySellDto) {
        int id = buySellDto.getTransaction_id();
        Transaction transactionForBuy = transactionDao.getById(id);
        Optional<Client> clientBuy = clientDao.getById(buySellDto.getUserId());
        Optional<Client> clientSell = clientDao.getById(transactionForBuy.getUserId());

        Optional<Wallet> walletBuyUser = walletDao.getById(clientBuy.get().getId())
                .stream()
                .filter(wallet -> wallet.getCompanyName().equals(buySellDto.getCompanyName()))
                .findAny();

        if (!walletBuyUser.isPresent()) {
            walletBuyUser = Optional.ofNullable(walletDao.save(clientBuy.get().getId(), buySellDto.getCompanyName()));
        }

        //FIXME: edit stream logic
        if (walletBuyUser.equals(null)) {
            return "Ошибка создания кошелька, попробуйте снова";
        }
        double price = buySellDto.getPrice() * buySellDto.getCounterOfStocks();
        double moneyAfterTransaction = clientBuy.get().getCash() - buySellDto.getPrice() * buySellDto.getCounterOfStocks();

        if (moneyAfterTransaction >= 0) {
            clientSell.get().setCash(clientSell.get().getCash() + price);
            clientBuy.get().setCash(clientBuy.get().getCash() - price);
            transactionForBuy.setTransactionalType("SELLED");
            transactionForBuy.setAmount(transactionForBuy.getAmount() - buySellDto.getCounterOfStocks());
            transactionDao.update(transactionForBuy);
            transactionDao.save(new Transaction() {{
                setAmount(buySellDto.getCounterOfStocks());
                setTransactionalType("BOUGHT");
                setPrice(buySellDto.getPrice());
                setCompanyId(companyDao.getByCompanyName(buySellDto.getCompanyName()).getCompanyId());
                setUserId(buySellDto.getUserId());
                setLocalDateTime(LocalDateTime.now());
            }});
            walletBuyUser.get().setCounterOfStocks(buySellDto.getCounterOfStocks());
            walletDao.update(walletBuyUser);
            clientDao.updateClient(clientBuy);
            clientDao.updateClient(clientSell);
            return "Сделка успешно произведена!";
        } else {
            return "У вас недостаточно денег для совершения сделки.";
        }
    }

    @Override
    public List<HistoryDto> getTransactionHistory(int company_id) {
        List<Transaction> all = transactionDao.getAll();
        System.out.println(all);
        return all.stream()
                .filter(transaction -> transaction.getCompanyId() == company_id)
                .filter(transaction -> transaction.getTransactionalType().equals("BOUGHT") || transaction.getTransactionalType().equals("SELLED"))
                .map(transaction -> {
                    HistoryDto historyDto = new HistoryDto();
                    historyDto.setPrice(transaction.getPrice());
                    historyDto.setStatus(transaction.getTransactionalType());
                    historyDto.setLocalDateTime(transaction.getLocalDateTime());
                    historyDto.setCounterOfStocks(transaction.getAmount());
                    return historyDto;
                })
                .toList();
    }

    @Override
    public List<Transaction> getCompletedTransaction(int id) {
        return transactionDao.getAllById(id).stream()
                .filter(transaction -> transaction.getTransactionalType().equals("SELLED") || transaction.getTransactionalType().equals("BOUGHT"))
                .toList();
    }

    @Override
    public List<ChartDto> getAllFinishedTransactionForCompany(int company_id) {
        System.out.println(company_id);
        System.out.println(transactionDao.getAllByCompanyId(company_id));
        return transactionDao.getAllByCompanyId(company_id).stream()
                .filter(transaction -> transaction.getTransactionalType().equals("BOUGHT"))
                .map(transaction -> {
                    ChartDto chartDto = new ChartDto();
                    chartDto.setPrice(transaction.getPrice());
                    chartDto.setTime(String.valueOf(transaction.getLocalDateTime()));
                    return chartDto;
        }).toList();
    }
}
