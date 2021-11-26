package org.example.server.model;

import org.example.server.config.AuthenticationConfig;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class UserSession {
    private String  token; // token= identifiant unique
    private Boolean connected;
    private Date    expiryDate;

    public UserSession() {
        this.connected = true;
        this.expiryDate = calculateExpiryDate(AuthenticationConfig.TIMEOUT_IN_MINUTES);
        this.token = UUID.randomUUID().toString();
    }

    private Date calculateExpiryDate(int expiration) {
        final Calendar currentTime = Calendar.getInstance(); // initialise un calendar
        currentTime.setTimeInMillis(new Date().getTime()); // l'heure actuelle
        currentTime.add(Calendar.MINUTE, expiration); // l'heure précédente + expiration en minute car .minute, si .second expiration = seconde
        return (new Date(currentTime.getTime().getTime())); // reconvertit le calendar en date
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "token='" + token + '\'' +
                ", connected=" + connected +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
