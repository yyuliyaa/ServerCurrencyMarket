package com.currencymarket.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientServerCommunication {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public ClientServerCommunication(Socket socket) {
        this.socket = socket;
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("can't initialise", e);
        }
    }

    public synchronized void writeObject(Object object) {
        try {
            outputStream.writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized Object readObject() {
        try {
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
