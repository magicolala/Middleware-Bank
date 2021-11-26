package org.example.domain.service;

import org.example.domain.model.CreditCard;
import org.example.server.application.AuthenticationException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBankService extends Remote {
    double getBalance(CreditCard creditCard) throws AuthenticationException, RemoteException;

    boolean deposit(double amount, CreditCard creditCard) throws AuthenticationException, RemoteException;

    boolean withdrawal(double amount, CreditCard creditCard) throws AuthenticationException, RemoteException;
}
