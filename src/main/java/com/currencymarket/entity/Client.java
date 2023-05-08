package com.currencymarket.entity;

import java.io.Serializable;


public class Client implements Serializable {
    private int id;
    private String username;
    private String password;
    private String isActive;
    private double cash;
    private String role;

    enum Role {
        ADMIN,
        CLIENT
    }

    enum ClientStatus {
        ACTIVE,
        BANNED
    }

    public Client(int id, String username, String password, String isActive, double cash, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.cash = cash;
        this.role = role;
    }

    public Client() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String isActive() {
        return isActive;
    }

    public void setActive(String active) {
        isActive = active;
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

    public Client(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", cash=" + cash +
                ", role=" + role +
                '}';
    }


}
