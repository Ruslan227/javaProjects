package ru.ifmo.rain.dorofeev.bank;

import ru.ifmo.test.common.bank.Account;
import ru.ifmo.test.common.bank.Person;

import java.io.Serializable;
import java.util.Map;

public class LocalPerson implements Person, Serializable {
    private final String name;
    private final String surname;
    private final String passport;
    private final Map<String, Account> accounts;

    LocalPerson(String name, String surname, String passport, Map<String, Account> accounts) {
        super();
        this.name = name;
        this.surname = surname;
        this.passport = passport;
        this.accounts = accounts;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public Account getAccount(String id) {
        return accounts.get(id);
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public String getPassport() {
        return passport;
    }

    public void addAccount(String id, Account account) {
        accounts.putIfAbsent(id, account);
    }
}
