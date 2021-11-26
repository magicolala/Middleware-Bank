package org.example.server.application;

import org.example.domain.model.CreditCard;
import org.example.domain.model.UserAccount;
import org.example.domain.service.IUserService;
import org.example.server.config.AuthenticationConfig;
import org.example.server.model.UserSession;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserServiceImpl implements IUserService {

    private Map<Integer, UserAccount> userAccounts;
    private Map<String, UserSession>  userSessions;
    private Map<String, Integer>      connectedUsers;
    private Map<String, Integer>      creditCardUsers;
    private Map<Integer, CreditCard>  usersCreditCard;

    private int rank = 0;

    public UserServiceImpl() {
        this.userAccounts = new HashMap<>();
        this.userSessions = new HashMap<>();
        this.connectedUsers = new HashMap<>();
        this.creditCardUsers = new HashMap<>();
        this.usersCreditCard = new HashMap<>();
    }

    @Override
    public void register(String name, String password) throws RemoteException, AuthenticationException {
        rank++;
        Integer     newId          = this.userAccounts.size() + 1;
        UserAccount newUserAccount = new UserAccount(newId, name, password, AuthenticationConfig.GIFT);
        for (UserAccount currentUserAccount : this.userAccounts.values()) {
            if (newUserAccount.equals(currentUserAccount)) {
                System.out.println(rank + " L'utilisateur " + currentUserAccount + " existe déjà !");
                throw new AuthenticationException("L'utilisateur existe déjà !");
            }
        }
        this.userAccounts.put(newId, newUserAccount);
        String creditCardNumber = UUID.randomUUID().toString();
        this.creditCardUsers.put(creditCardNumber, newUserAccount.getId());
        this.usersCreditCard.put(newUserAccount.getId(), new CreditCard(creditCardNumber));
        System.out.println(rank + " Utilisateur ajouté: " + newUserAccount);
    }

    @Override
    public CreditCard login(String name, String password) throws RemoteException {
        rank++;
        System.out.println("Name = " + name + " Password = " + password);
        if (name != null && password != null) {
            for (UserAccount currentUserAccount : this.userAccounts.values()) {
                System.out.println("Current user account = " + currentUserAccount);
                if ((name.equals(currentUserAccount.getName())) &&
                        (password.equals(currentUserAccount.getPassword()))) { // l'user existe
                    UserSession userSession = new UserSession();
                    this.connectedUsers.put(userSession.getToken(), currentUserAccount.getId());
                    this.userSessions.put(userSession.getToken(), userSession);
                    System.out.println(rank + " Connexion Réussie! ");
                    CreditCard temporaryCreditCard = this.usersCreditCard.get(currentUserAccount.getId());
                    temporaryCreditCard.setSecurityId(userSession.getToken());
                    return temporaryCreditCard;
                }
            }
        }
        System.out.println(rank + " Connexion échouée! ");
        return null; // le user n'existe pas
    }

    private Boolean isConnected(String token) {
        rank++;
        UserSession currentUserSession = this.userSessions.get(token);
        System.out.println("Current user session = " + currentUserSession);
        return (currentUserSession != null) &&
                currentUserSession.getToken().equals(token) &&
                (currentUserSession.getExpiryDate().getTime() > (new Date().getTime())) &&
                currentUserSession.getConnected();
    }

    @Override
    public UserAccount findUserAccountByCreditCard(CreditCard creditCard) throws AuthenticationException {
        if (this.isConnected(creditCard.getSecurityId())) {
            Integer userAccountId = this.creditCardUsers.get(creditCard.getId());
            return this.userAccounts.get(userAccountId);
        } else {
            throw new AuthenticationException("L'utilisateur n'est pas connecté ou le token est invalide.");
        }
    }

    @Override
    public void saveUserAccount(UserAccount userAccount) {
        if (this.userAccounts.containsKey(userAccount.getId()))
            this.userAccounts.put(userAccount.getId(), userAccount);
    }
}