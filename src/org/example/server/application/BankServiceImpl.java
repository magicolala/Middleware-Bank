package org.example.server.application;

import org.example.domain.model.CreditCard;
import org.example.domain.model.UserAccount;
import org.example.domain.service.IBankService;
import org.example.domain.service.IUserService;

import java.rmi.RemoteException;

public class BankServiceImpl implements IBankService {

    private IUserService userService;

    public BankServiceImpl(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public double getBalance(CreditCard creditCard) throws AuthenticationException, RemoteException {
        UserAccount userAccount = this.userService.findUserAccountByCreditCard(creditCard);
        return userAccount.getBalance();
    }

    @Override
    public boolean deposit(double amount, CreditCard creditCard) throws AuthenticationException, RemoteException {
        UserAccount userAccount = this.userService.findUserAccountByCreditCard(creditCard);
        if (amount > 0) {
            userAccount.setBalance(userAccount.getBalance() + amount);
            this.userService.saveUserAccount(userAccount);
            return true;
        } else
            return false;
    }

    @Override
    public boolean withdrawal(double amount, CreditCard creditCard) throws AuthenticationException, RemoteException {
        UserAccount userAccount = this.userService.findUserAccountByCreditCard(creditCard);
        double      result      = userAccount.getBalance() - amount;
        if (result >= 0 && amount > 0) {
            userAccount.setBalance(result);
            this.userService.saveUserAccount(userAccount);
            return true;
        } else
            return false;
    }
}
