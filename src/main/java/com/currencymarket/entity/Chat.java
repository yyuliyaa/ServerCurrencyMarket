package com.currencymarket.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Chat implements Serializable {
    private int message_id;
    private int client_id;
    private String message;
    private LocalDateTime localDateTime;
    private Client client;

    public Chat() {
    }

    public Chat(int client_id, String message, LocalDateTime localDateTime) {
        this.client_id = client_id;
        this.message = message;
        this.localDateTime = localDateTime;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "message_id=" + message_id +
                ", client_id=" + client_id +
                ", message='" + message + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
