package com.currencymarket;

import com.currencymarket.command.ClientAction;
import com.currencymarket.dto.admindto.ClientInformationDto;
import com.currencymarket.dto.admindto.CompanyRequestStatus;
import com.currencymarket.dto.chatdto.MessageDto;
import com.currencymarket.dto.clientdto.PersonalInfoDto;
import com.currencymarket.dto.clientdto.UpdateCashDto;
import com.currencymarket.dto.companydto.CompanyMarketStatus;
import com.currencymarket.dto.companydto.CreateCompanyDto;
import com.currencymarket.dto.companydto.CurrentCompanyStatusDto;
import com.currencymarket.entity.Transaction;
import com.currencymarket.utils.ClientServerCommunication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Test {

    static ClientServerCommunication clientServerConnection;

    public static void main(String[] args) throws IOException {
        clientServerConnection = new ClientServerCommunication(new Socket("127.0.0.1", 8001));
        getCompletedTransaction();
//        getCompanyStatus();
//        System.out.println(getCompanyShares());
    }

    private static List<ClientInformationDto> getAll() {
        clientServerConnection.writeObject(ClientAction.GET_ALL_CLIENTS);
        return (List<ClientInformationDto>) clientServerConnection.readObject();
    }

    private static String deleteById() {
        clientServerConnection.writeObject(ClientAction.DELETE_CLIENT);
        clientServerConnection.writeObject(1);
        return (String) clientServerConnection.readObject();
    }


    private static List<PersonalInfoDto> getPersonalInfo() {
        clientServerConnection.writeObject(ClientAction.GET_PERSONAL_INFO);
        clientServerConnection.writeObject(1);
        return (List<PersonalInfoDto>) clientServerConnection.readObject();

    }

    private static String update() {
        CompanyRequestStatus companyRequestStatus = new CompanyRequestStatus();
        companyRequestStatus.setOwner("Ilya");
        companyRequestStatus.setCompanyStatus("CONFIRMED");
        companyRequestStatus.setCompanyName("BIT");
        clientServerConnection.writeObject(ClientAction.UPDATE_COMPANY_STATUS);
        clientServerConnection.writeObject(companyRequestStatus);
        return "asd";
    }

    private static String gelAllCopmanies() {
        clientServerConnection.writeObject(ClientAction.GET_COMPANY_STATUS);
        List<CompanyRequestStatus> list = (List<CompanyRequestStatus>) clientServerConnection.readObject();
        System.out.println(list);
        return "sa";
    }

    private static String getCompanyStatus() {
        clientServerConnection.writeObject(ClientAction.GET_COMPANY_STATUS_FOR_USER);
        clientServerConnection.writeObject(10);
        List<CurrentCompanyStatusDto> list = (List<CurrentCompanyStatusDto>) clientServerConnection.readObject();
        System.out.println(list);
        return "sa";
    }

    private static String updateCash() {
        clientServerConnection.writeObject(ClientAction.UPDATE_CASH);
        clientServerConnection.writeObject(new UpdateCashDto() {{
            setId(10);
            setCash(106.5f);
        }});
        String s = (String) clientServerConnection.readObject();
        System.out.println(s);
        return "sa";
    }

    private static void getCompanyShares() {
        clientServerConnection.writeObject(ClientAction.GET_COMPANY_SHARES);
        List<CompanyMarketStatus> companyMarketStatuses = (List<CompanyMarketStatus>) clientServerConnection.readObject();
        System.out.println(companyMarketStatuses);
    }

    private static void send() {
        clientServerConnection.writeObject(ClientAction.SEND_MESSAGE);
        MessageDto messageDto = new MessageDto();
        messageDto.setMessage("Hello evetybode");
        messageDto.setId(10);
        clientServerConnection.writeObject(messageDto);
    }

    private static void getAllMes() {
        clientServerConnection.writeObject(ClientAction.GET_MESSAGE);
        System.out.println(clientServerConnection.readObject());
    }

    private static void createCompany() {
        CreateCompanyDto companyDto = new CreateCompanyDto();
        companyDto.setCompanyInfo("ads");
        companyDto.setCompanyName("ASD");
        companyDto.setStockPrice(3);
        companyDto.setCounterOfStocks(1231);
        companyDto.setOwner_id(15);
        clientServerConnection.writeObject(ClientAction.CREATE_COMPANY);
        clientServerConnection.writeObject(companyDto);
        System.out.println(clientServerConnection.readObject());

    }

    private static void getAssets() {

        clientServerConnection.writeObject(ClientAction.GET_ASSETS);
        clientServerConnection.writeObject(16);
        System.out.println(clientServerConnection.readObject());

    }

    private static void getTransactionHistory() {

        clientServerConnection.writeObject(ClientAction.GET_HISTORY_OF_TRANSACTION_FOR_COMPANY);
        clientServerConnection.writeObject(3);
        System.out.println(clientServerConnection.readObject());

    }

    private static void getCompletedTransaction() throws IOException {
        clientServerConnection.writeObject(ClientAction.GET_COMPLETED_TRANSACTION);
        clientServerConnection.writeObject(4);
        List<Transaction> transactionList = (List<Transaction>) clientServerConnection.readObject();
        System.out.println(transactionList);
        FileOutputStream writeData = new FileOutputStream("peopledata.txt");
        ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

        writeStream.writeObject(transactionList);
        writeStream.flush();
        writeStream.close();
    }


}
