package com.Bank.app.model.requests;

public class AccountRequests {
    private final String number;
    private final String userEmail;

    public AccountRequests(String number,
                           String userEmail) {
        this.number = number;
        this.userEmail = userEmail;
    }

    public String getNumber() {
        return number;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
