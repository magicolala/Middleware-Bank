package org.example.server.infrastructure.service;

import org.example.domain.service.IBankService;
import org.example.domain.service.IUserService;
import org.example.server.application.BankServiceImpl;
import org.example.server.application.UserServiceImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiService {
    private Registry registry;

    public RmiService() throws RemoteException {
        // Démarrage du registry et enregistrement de la machine serveur
        this.registry = LocateRegistry.createRegistry(2004);
        System.out.println("Remote server is listening at port 2004");
    }

    public void bindServices() throws RemoteException {
        // Instanciation de l'objet servi
        IUserService userService = new UserServiceImpl();
        // Création de l'objet proxy (de même type que l'objet servi)
        IUserService userServiceStub = (IUserService) UnicastRemoteObject
                .exportObject((IUserService) userService, 0);

        IBankService bankService = new BankServiceImpl(userService);
        // Création de l'objet proxy (de même type que l'objet servi)
        IBankService bankServiceStub = (IBankService) UnicastRemoteObject
                .exportObject((IBankService) bankService, 0);

        // Enregistrement des services fournis par le serveur à partir des objets proxy (stub)
        this.registry.rebind("userService", userServiceStub);
        this.registry.rebind("bankService", bankServiceStub);

        System.out.println("BindService is done !");
    }
}
