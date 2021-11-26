package org.example.domain.model;

import java.io.Serializable;

public class CreditCard implements Serializable {

    private String id;
    private String securityId;       // Represente le code PIN de la carte et le fait qu'un utilisateur là utilisé pour se connecter

    public CreditCard(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id='" + id + '\'' +
                ", securityId='" + securityId + '\'' +
                '}';
    }
}
