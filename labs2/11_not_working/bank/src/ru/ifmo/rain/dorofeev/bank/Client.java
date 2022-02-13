package ru.ifmo.rain.dorofeev.bank;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import ru.ifmo.test.common.bank.Bank;
import ru.ifmo.test.common.bank.BankClient;
import ru.ifmo.test.common.bank.Person;

public class Client implements BankClient {
    private Server server = new Server();
    private Bank bank;

    @Override
    public void start(int port) {
        server.start(port);
        try {
            Registry registry = LocateRegistry.getRegistry(null, port);
            bank = (Bank) registry.lookup("//localhost/bank");
        } catch (final RemoteException | NotBoundException er) {
            System.out.println(er.getMessage());
        }
    }

    @Override
    public int change(String name, String surname, String passport, String accountName, String modification) throws RemoteException {
        Person person = bank.createPerson(name, surname, passport);
        if (person.getName().equals(name) && person.getSurname().equals(surname)) {
            bank.createPersonAccount(accountName, person);
            person.getAccount(accountName).setAmount(person.getAccount(accountName).getAmount() + Integer.parseInt(modification));
            return person.getAccount(accountName).getAmount();
        } else {
            return 0;
        }
    }

    @Override
    public void close() {
        server.close();
    }
}
