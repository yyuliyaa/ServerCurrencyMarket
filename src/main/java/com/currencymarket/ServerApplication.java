package com.currencymarket;

import com.currencymarket.controller.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApplication {

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8001);
            System.out.println("Server started");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection established\n" +
                        "IP:" + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                Controller client = new Controller(clientSocket);
                new Thread(client).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
