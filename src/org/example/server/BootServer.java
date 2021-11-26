package org.example.server;

import org.example.server.infrastructure.service.RmiService;

import java.rmi.RemoteException;

public class BootServer {

    public static void main(String[] args) throws RemoteException {
        RmiService rmiService = new RmiService();
        rmiService.bindServices();
    }
}
