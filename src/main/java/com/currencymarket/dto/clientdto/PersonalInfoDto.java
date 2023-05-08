package com.currencymarket.dto.clientdto;

import java.io.Serializable;

public class PersonalInfoDto implements Serializable {
    private String username;
    private double cash;
    private String role;

    private String status_type;

    private int counterOfStocks;

    private String companyName;

    public PersonalInfoDto() {
    }

    public PersonalInfoDto(String username, double cash, String role, String status_type, int counterOfStocks, String companyName) {
        this.username = username;
        this.cash = cash;
        this.role = role;
        this.status_type = status_type;
        this.counterOfStocks = counterOfStocks;
        this.companyName = companyName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus_type() {
        return status_type;
    }

    public void setStatus_type(String status_type) {
        this.status_type = status_type;
    }

    public int getCounterOfStocks() {
        return counterOfStocks;
    }

    public void setCounterOfStocks(int counterOfStocks) {
        this.counterOfStocks = counterOfStocks;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "PersonalInfoDto{" +
                "username='" + username + '\'' +
                ", cash=" + cash +
                ", role='" + role + '\'' +
                ", status_type='" + status_type + '\'' +
                ", counterOfStocks=" + counterOfStocks +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
