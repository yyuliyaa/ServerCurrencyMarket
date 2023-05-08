package com.currencymarket.controller;

import com.currencymarket.command.ClientAction;
import com.currencymarket.command.Status;
import com.currencymarket.dto.ChartDto;
import com.currencymarket.dto.SignUpDto;
import com.currencymarket.dto.admindto.ClientInformationDto;
import com.currencymarket.dto.admindto.CompanyRequestStatus;
import com.currencymarket.dto.carddto.CardDto;
import com.currencymarket.dto.chatdto.MessageDto;
import com.currencymarket.dto.clientdto.AssetDto;
import com.currencymarket.dto.clientdto.ChangePasswordDto;
import com.currencymarket.dto.clientdto.HomePageDto;
import com.currencymarket.dto.clientdto.UpdateCashDto;
import com.currencymarket.dto.companydto.CompanyMarketStatus;
import com.currencymarket.dto.companydto.CreateCompanyDto;
import com.currencymarket.dto.companydto.CurrentCompanyStatusDto;
import com.currencymarket.dto.transactiondto.BuySellDto;
import com.currencymarket.entity.Company;
import com.currencymarket.entity.Transaction;
import com.currencymarket.entity.Wallet;
import com.currencymarket.service.*;
import com.currencymarket.service.impl.*;
import com.currencymarket.utils.ClientServerCommunication;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Controller implements Runnable {
    private final ClientServerCommunication connectionTCP;
    private final RegistrationService registrationService;
    private final ClientService clientService;
    private final AdminService adminService;
    private final CompanyService companyService;
    private final WalletService walletService;
    private final ChatService chatService;
    private final TransactionService transactionService;
    private final CardService cardService;
    private List<Socket> socketList = new ArrayList<>();


//    private static Logger logger = LogManager.getLogger(Controller.class);

    public Controller(Socket socket) {
        connectionTCP = new ClientServerCommunication(socket);
        registrationService = new RegistrationServiceImpl();
        adminService = new AdminServiceImpl();
        clientService = new ClientServiceImpl();
        companyService = new CompanyServiceImpl();
        walletService = new WalletServiceImpl();
        socketList.add(socket);
        chatService = new ChatServiceImpl();
        transactionService = new TransactionServiceImpl();
        cardService = new CardServiceImpl();
    }

    @Override
    public void run() {
        while (true) {
            ClientAction clientAction = (ClientAction) connectionTCP.readObject();
            switch (clientAction) {
                case SIGN_UP -> {
                    SignUpDto signUpRequest = (SignUpDto) connectionTCP.readObject();
                    boolean register = registrationService.register(signUpRequest);
                    if (register) {
                        HomePageDto homePageDto = registrationService.sign_in(signUpRequest);
                        connectionTCP.writeObject(true);
                        connectionTCP.writeObject(homePageDto);
                    } else {
                        connectionTCP.writeObject(false);
                    }
                }
                case SIGN_IN -> {
                    SignUpDto signUpDto = (SignUpDto) connectionTCP.readObject();
                    Status status = registrationService.checkUser(signUpDto);
                    if (status.equals(Status.ACCEPTED)) {
                        connectionTCP.writeObject(Status.ACCEPTED);
                        connectionTCP.writeObject(registrationService.sign_in(signUpDto));
                    } else if (status.equals(Status.BAN)) {
                        connectionTCP.writeObject(Status.BAN);
                    } else connectionTCP.writeObject(Status.INVALID_LOGIN_OR_PASSWORD);
                }
                case GET_ALL_CLIENTS -> {
                    List<ClientInformationDto> all = adminService.getAll();
                    connectionTCP.writeObject(all);
                    System.out.println(all);

                }
                case DELETE_CLIENT -> {
                    int id = (int) connectionTCP.readObject();
                    connectionTCP.writeObject(adminService.deleteClient(id));
                }
                case GET_PERSONAL_INFO -> {
                    int id = (int) connectionTCP.readObject();
                    connectionTCP.writeObject(clientService.getPersonalInfo(id));
                }
                case UPDATE -> {
                    ClientInformationDto clientInformationDto = (ClientInformationDto) connectionTCP.readObject();
                    connectionTCP.writeObject(clientService.updateClient(clientInformationDto));
                }
                case UPDATE_COMPANY_STATUS -> {
                    CompanyRequestStatus companyRequestStatus = (CompanyRequestStatus) connectionTCP.readObject();
                    String response = companyService.updateCompanyStatus(companyRequestStatus);
                    connectionTCP.writeObject(response);
                }
                case GET_COMPANY_STATUS -> {
                    List<CompanyRequestStatus> allCompanies = companyService.getAllCompanies();
                    System.out.println(allCompanies);
                    connectionTCP.writeObject(allCompanies);
                }
                case CREATE_COMPANY -> {
                    CreateCompanyDto companyDto = (CreateCompanyDto) connectionTCP.readObject();
                    Company company = companyService.createCompany(companyDto);
                    System.out.println(company);
                    connectionTCP.writeObject(company);
                }
                case GET_COMPANY_STATUS_FOR_USER -> {
                    int id = (int) connectionTCP.readObject();
                    List<CurrentCompanyStatusDto> companyByClientId = companyService.getCompanyByClientId(id);
                    System.out.println(companyByClientId);
                    connectionTCP.writeObject(companyByClientId);

                }
                case UPDATE_CASH -> {
                    UpdateCashDto updateCashDto = (UpdateCashDto) connectionTCP.readObject();
                    double cash = clientService.updateCash(updateCashDto);

                    connectionTCP.writeObject(cash);
                }
                case GET_COMPANY_SHARES -> {
                    List<CompanyMarketStatus> companyShares = companyService.getCompanyShares();
                    connectionTCP.writeObject(companyShares);
                }
                case SEND_MESSAGE -> {
                    MessageDto messageDto = (MessageDto) connectionTCP.readObject();
                    System.out.println(messageDto);
                    chatService.sendMessage(messageDto);
                }
                case GET_MESSAGE -> {
                    connectionTCP.writeObject(chatService.gelAllMessage());
                }
                case GET_ASSETS -> {
                    int id = (int) connectionTCP.readObject();
                    List<AssetDto> boughtAssets = transactionService.getBoughtAssets(id);
                    connectionTCP.writeObject(boughtAssets);

                }
                case GET_ACTIVE_TRANSACTION -> {
                    connectionTCP.writeObject(transactionService.getActiveTransaction());
                }
                case SELL_ASSETS -> {
                    BuySellDto buySellDto = (BuySellDto) connectionTCP.readObject();
                    transactionService.addTransaction(buySellDto);
                }
                case BUY_ASSETS -> {
                    BuySellDto buySellDto = (BuySellDto) connectionTCP.readObject();
                    transactionService.buyAssets(buySellDto);
                }
                case GET_HISTORY_OF_TRANSACTION_FOR_COMPANY -> {
                    int company_id = (int) connectionTCP.readObject();
                    connectionTCP.writeObject(transactionService.getTransactionHistory(company_id));
                }
                case GET_COMPANIES -> {
                    connectionTCP.writeObject(companyService.getAllConfirmedCompanies());
                }
                case GET_WALLET_LIST -> {
                    int userId = (int) connectionTCP.readObject();
                    System.out.println(userId);
                    List<Wallet> allUserWallet = walletService.getAllUserWallet(userId);
                    System.out.println(allUserWallet);
                    connectionTCP.writeObject(allUserWallet);
                }
                case GET_WAITING_COMPANIES -> {
                    connectionTCP.writeObject(companyService.getAllWaitingCompanies());
                }
                case CHANGE_PASSWORD -> {
                    ChangePasswordDto changePasswordDto = (ChangePasswordDto) connectionTCP.readObject();
                    boolean response = clientService.changePassword(changePasswordDto);
                    connectionTCP.writeObject(response);
                }
                case GET_COMPLETED_TRANSACTION -> {
                    int id = (int) connectionTCP.readObject();
                    List<Transaction> completedTransaction = transactionService.getCompletedTransaction(id);
                    System.out.println(completedTransaction);
                    connectionTCP.writeObject(completedTransaction);
                }
                case GET_WEEK_CHART_REPORT -> {
                    int company_id = (int) connectionTCP.readObject();
                    List<ChartDto> allFinishedTransactionForCompany = transactionService.getAllFinishedTransactionForCompany(company_id);
                    System.out.println(allFinishedTransactionForCompany);
                    connectionTCP.writeObject(allFinishedTransactionForCompany);
                }
                case ACTIVATE_CARD -> {
                    CardDto cardDto = (CardDto) connectionTCP.readObject();
                    System.out.println(cardDto);
                    cardService.activateCard(cardDto);
                    connectionTCP.writeObject("Карта успешно активирована");
                }
                case GET_CARD -> {
                    int userId = (int) connectionTCP.readObject();
                    System.out.println(userId);
                    CardDto cart = cardService.getCart(userId);
                    System.out.println(cart);
                    connectionTCP.writeObject(cart);
                }
                case GET_CASH_FROM_CARD -> {
                    int userId = (int) connectionTCP.readObject();
                    float cash = cardService.getCashFromCard(userId);
                    connectionTCP.writeObject(cash);
                }
                case EXIT -> {
                    connectionTCP.close();
                }
            }
        }
    }


}
