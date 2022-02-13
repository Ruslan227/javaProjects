package ru.ifmo.rain.dorofeev.bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RemotePerson extends UnicastRemoteObject implements Person {
    private final String name;
    private final String surname;
    private final String passport;
    private final Map<String, Account> accounts;

    public RemotePerson(String name,
                        String surname,
                        String passport,
                        int port) throws RemoteException {
        super(port);
        this.name = name;
        this.surname = surname;
        this.passport = passport;
        this.accounts = new ConcurrentHashMap<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Account getAccount(String id) {
        return accounts.get(id);
    }

    public Account addAccount(String id, Account account) {
        return accounts.putIfAbsent(id, account);
    }

    Set<String> getAccounts() {
        return accounts.keySet();
    }

    @Override
    public String getSurname() {
        return this.surname;
    }

    @Override
    public String getPassport() {
        return this.passport;
    }
}
