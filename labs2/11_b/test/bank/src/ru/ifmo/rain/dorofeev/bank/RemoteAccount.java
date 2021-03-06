package ru.ifmo.rain.dorofeev.bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteAccount extends UnicastRemoteObject implements Account {
    private final String id;
    private int amount;

    public RemoteAccount(final String id, int port) throws RemoteException {
        super(port);
        this.id = id;
        amount = 0;
    }

    public String getId() {
        return id;
    }

    public synchronized int getAmount() {
        return amount;
    }

    public synchronized void setAmount(final int amount) {
        this.amount = amount;
    }
}
