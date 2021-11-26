package org.example.client.infrastructure;

import org.example.domain.model.CreditCard;
import org.example.domain.service.IUserService;
import org.example.server.application.AuthenticationException;

import java.rmi.RemoteException;
import java.util.Scanner;

public class UserApiService {

    private IUserService service;
    private String       userName;

    public UserApiService(IUserService service) {
        this.service = service;
    }

    public CreditCard signupWithScanner() throws RemoteException {
        Scanner saisie = new Scanner(System.in);
        System.out.println(" --->Signup<---  ");
        System.out.print("Entrez votre nom : ");
        String name = saisie.nextLine();
        System.out.print("Entrez votre mot de passe : ");
        String     password   = saisie.nextLine();
        CreditCard creditCard = service.login(name, password);
        if (creditCard != null) {
            this.userName = name;
            System.out.println("Connexion réussie , Crédit Card = " + creditCard);
        } else {
            System.out.println("Connexion échouée , Crédit Card = null ");
        }
        return creditCard;
    }

    public String getUserName() {
        return userName;
    }

    public void registerWithScanner() throws RemoteException {
        Scanner saisie = new Scanner(System.in);
        System.out.println(" --->Register<---  ");
        System.out.print("Entrez votre nom : ");
        String name = saisie.nextLine();
        System.out.print("Entrez votre mot de passe : ");
        String password = saisie.nextLine();
        try {
            service.register(name, password);
            System.out.println("Inscription réussie !");
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }
}
