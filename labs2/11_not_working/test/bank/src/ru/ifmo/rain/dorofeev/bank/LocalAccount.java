package ru.ifmo.rain.dorofeev.bank;

import java.io.Serializable;

public class LocalAccount implements Account, Serializable {
    private final String id;
    private int amount;

    public LocalAccount(final String id, int amount) {
        super();
        this.id = id;
        this.amount = amount;
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
