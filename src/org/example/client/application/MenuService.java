package org.example.client.application;

import org.example.client.infrastructure.BankApiService;
import org.example.client.infrastructure.RmiClientService;
import org.example.client.infrastructure.UserApiService;
import org.example.domain.model.CreditCard;
import org.example.domain.service.IBankService;
import org.example.domain.service.IUserService;
import org.example.server.application.AuthenticationException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class MenuService {

    private UserApiService userApiService;
    private BankApiService bankApiService;
    private int            choice;
    private int            closingChoice;

    public MenuService() throws NotBoundException, RemoteException {
        Registry     registry    = RmiClientService.getRegistry();
        IUserService userService = (IUserService) registry.lookup("userService");
        IBankService bankService = (IBankService) registry.lookup("bankService");
        this.userApiService = new UserApiService(userService);
        this.bankApiService = new BankApiService(bankService);
        this.choice = 0;
        this.closingChoice = -1;
    }

    public void runMenu() throws RemoteException, AuthenticationException {
        CreditCard creditCard = null;
        do {
            if (creditCard == null)
                creditCard = this.notConnectedMenu(this.userApiService);
            else
                creditCard = this.connectedMenu(creditCard, this.userApiService, this.bankApiService);
        } while (this.choice != this.closingChoice);
    }

    public CreditCard connectedMenu(CreditCard creditCard, UserApiService userApiService, BankApiService bankApiService) throws RemoteException, AuthenticationException {
        System.out.println("\n\nBonjour " + userApiService.getUserName());
        System.out.println("***1*** Vérification du solde");
        System.out.println("***2*** Retrait");
        System.out.println("***3*** Dépôt");
        System.out.println("***4*** Quitter");
        System.out.print("Choisir une option entre 1, 2 , 3 & 4 : ");
        Scanner saisie = new Scanner(System.in);
        this.closingChoice = 4;
        choice = saisie.nextInt();
        double amount;
        switch (choice) {
            case 1:
                System.out.println("Solde = " + bankApiService.getBalance(creditCard));
                break;
            case 2:
                System.out.print("Entrez votre montant de retrait: ");
                amount = saisie.nextDouble();
                System.out.println("Status du retrait = " + bankApiService.withdrawal(amount, creditCard));
                break;
            case 3:
                System.out.print("Entrez votre montant de dépôt: ");
                amount = saisie.nextDouble();
                System.out.println("Status du dépôt = " + bankApiService.deposit(amount, creditCard));
                break;
            case 4:
                System.out.println("Bye Bye! ");
                break;
            default:
                System.out.println("Choix incorrect! ");
                break;
        }
        System.out.println("\n______________________________________________");
        return creditCard;
    }

    public CreditCard notConnectedMenu(UserApiService userApiService) throws RemoteException {
        System.out.println("\n\n***1*** Connexion");
        System.out.println("***2*** Inscription");
        System.out.println("***3*** Quitter");
        System.out.print("Choisir une option entre 1, 2 & 3 : ");
        Scanner saisie = new Scanner(System.in);
        this.closingChoice = 3;
        choice = saisie.nextInt();
        switch (choice) {
            case 1:
                return userApiService.signupWithScanner();
            case 2:
                userApiService.registerWithScanner();
                break;
            case 3:
                System.out.println("Bye Bye! ");
                break;
            default:
                System.out.println("Choix incorrect! ");
                break;
        }
        System.out.println("\n______________________________________________");
        return null;
    }
}
