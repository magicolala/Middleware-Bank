package org.example.client;

import org.example.client.application.MenuService;
import org.example.server.application.AuthenticationException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class BootClient {

    public static void main(String[] args) throws RemoteException, NotBoundException, AuthenticationException {
        MenuService menuService = new MenuService();
        menuService.runMenu();
    }
}