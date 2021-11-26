package org.example.client.infrastructure;

import org.example.domain.model.CreditCard;
import org.example.domain.service.IBankService;
import org.example.server.application.AuthenticationException;

import java.rmi.RemoteException;

public class BankApiService {

    private IBankService service;

    public BankApiService(IBankService service) {
        this.service = service;
    }

    public double getBalance(CreditCard creditCard) throws AuthenticationException, RemoteException {
        return this.service.getBalance(creditCard);
    }

    public boolean deposit(double amount, CreditCard creditCard) throws AuthenticationException, RemoteException {
        return this.service.deposit(amount, creditCard);
    }

    public boolean withdrawal(double amount, CreditCard creditCard) throws AuthenticationException, RemoteException {
        return this.service.withdrawal(amount, creditCard);
    }
}