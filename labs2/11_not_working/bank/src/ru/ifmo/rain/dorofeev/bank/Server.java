package ru.ifmo.rain.dorofeev.bank;

import ru.ifmo.test.common.bank.Bank;
import ru.ifmo.test.common.bank.BankServer;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements BankServer {
    private final static int PORT = 8888;
    private static final String rmiName = "//localhost/bank";
    private static Registry registry;

    @Override
    public void start(int port) {
        try {
            final Bank bank = new RemoteBank(port);
            Bank stub = (Bank) UnicastRemoteObject.exportObject(bank, port);
            registry = LocateRegistry.createRegistry(port);
            registry.rebind(rmiName, stub);

        } catch (final RemoteException er) {
            System.out.println(er.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            registry.unbind(rmiName);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}