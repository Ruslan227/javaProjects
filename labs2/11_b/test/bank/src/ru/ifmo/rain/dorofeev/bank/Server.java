package ru.ifmo.rain.dorofeev.bank;

import java.net.MalformedURLException;
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
        } catch (final RemoteException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            registry.unbind(rmiName);
        } catch (RemoteException | NotBoundException e) {
            System.err.println(e.getMessage());
        }
    }

    // change
    public static void main(String[] args) throws RemoteException {
        try {
            final Bank bank = new RemoteBank(PORT);
            Naming.rebind("//localhost/bank", bank);
        } catch (final RemoteException e) {
            System.out.println("Cannot export object: " + e.getMessage());
            e.printStackTrace();
        } catch (final MalformedURLException e) {
            System.out.println("Malformed URL");
        }
        System.out.println("Server started");
    }
}