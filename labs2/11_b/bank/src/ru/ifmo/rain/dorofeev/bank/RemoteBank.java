package ru.ifmo.rain.dorofeev.bank;

import java.rmi.RemoteException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ru.ifmo.test.common.bank.Account;
import ru.ifmo.test.common.bank.Bank;
import ru.ifmo.test.common.bank.Person;

public class RemoteBank implements Bank {
    private final int port;
    private final ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Person> persons = new ConcurrentHashMap<>();

    public RemoteBank(final int port) {
        this.port = port;
    }

    @Override
    public Account createAccount(String id) throws RemoteException {
        if (accounts.containsKey(id)) {
            return accounts.get(id);
        }
        Account account = new RemoteAccount(id, port);
        accounts.putIfAbsent(id, account);
        return account;
    }

    @Override
    public Account getAccount(String id) {
        return accounts.get(id);
    }

    @Override
    public Person createPersonAccount(String id, Person person) throws RemoteException {
        String accountId = person.getPassport() + ":" + id;
        Account account = null;
        if (person instanceof LocalPerson) {
            account = new LocalAccount(accountId, 0);
            ((LocalPerson) person).addAccount(id, account);
        } else {
            account = new RemoteAccount(accountId, port);
            ((RemotePerson) persons.get(person.getPassport())).addAccount(id, account);
            accounts.putIfAbsent(accountId, account);
        }

        return person;
    }

    @Override
    public Person createPerson(String name, String surname, String passport) throws RemoteException {
        if (persons.containsKey(passport)) {
            return persons.get(passport);
        }

        Person person = new RemotePerson(name, surname, passport, port);
        persons.putIfAbsent(passport, person);
        return person;
    }

    @Override
    public Person getLocalPerson(String passport) throws RemoteException {
        Person person = persons.get(passport);
        if (person == null) {
            return null;
        }

        ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();
        Set<String> ids = ((RemotePerson) persons.get(person.getPassport())).getAccounts();
        LocalPerson localPerson = new LocalPerson(person.getName(), person.getSurname(), person.getPassport(), accounts);

        for (String id : ids) {
            localPerson.addAccount(id, new LocalAccount(person.getPassport() + ":" + id,
                    ((RemotePerson) persons.get(person.getPassport())).getAccount(id).getAmount()));
        }

        return localPerson;
    }

    @Override
    public Person getRemotePerson(String passport) {
        return persons.get(passport);
    }

}
