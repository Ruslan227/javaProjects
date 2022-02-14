package ru.ifmo.rain.dorofeev.bank;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client implements BankClient {
    private final Server server = new Server();
    private Bank bank;

    @Override
    public void start(int port) {
        server.start(port);
        try {
            Registry registry = LocateRegistry.getRegistry(null, port);
            bank = (Bank) registry.lookup("//localhost/bank");
        } catch (final RemoteException | NotBoundException e) {
            System.err.println(e.getMessage());
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

    public static void main(String[] args) {
        try {
            final Bank bank;
            try {
                bank = (Bank) Naming.lookup("//localhost/bank");
            } catch (final NotBoundException e) {
                System.out.println("Bank is not bound");
                return;
            } catch (final MalformedURLException e) {
                System.out.println("Bank URL is invalid");
                return;
            }

            String name, surname, passport, accountId;
            int change;

            try {
                name = args[0];
                surname = args[1];
                passport = args[2];
                accountId = args[3];
                change = Integer.parseInt(args[4]);
            } catch (Exception e) {
                System.out.println("ERROR: Wrong arguments format. Need <name> <surname> <passport> <account id> <change>");
                return;
            }

            Person person = bank.getRemotePerson(passport);
            if (person == null) {
                System.out.println("Creating new person");
                bank.createPerson(name, surname, passport);
            }

            if (!bank.getAccount(perso).contains(accountId)) { // bank.getPersonAccounts
                System.out.println("Creating account");
                Account account = bank.getAccount(accountId);
                if (account != null) {
                    System.out.println("ERROR: Account already exists for another person!");
                    return;
                }
                bank.createAccount(accountId);
            }

            Account account = bank.getAccount(accountId);
            System.out.println("Account id: " + account.getId());
            System.out.println("Money: " + account.getAmount());
            System.out.println("Adding money");
            account.setAmount(account.getAmount() + change);
            System.out.println("Money: " + account.getAmount());

        } catch (RemoteException e) {
            System.out.println("ERROR: Problems with remote." + e);
        }
    }
}
