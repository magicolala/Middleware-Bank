package org.example.domain.service;

import org.example.domain.model.CreditCard;
import org.example.domain.model.UserAccount;
import org.example.server.application.AuthenticationException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUserService extends Remote {

    void register(String name, String password) throws RemoteException, AuthenticationException;

    // renvoie une information qui servira à vérifier si l'user est connecté ou pas
    CreditCard login(String name, String password) throws RemoteException;

    UserAccount findUserAccountByCreditCard(CreditCard creditCard) throws RemoteException, AuthenticationException;

    void saveUserAccount(UserAccount userAccount) throws RemoteException;
}
