package com.Bank.app.model.requests;

public class PasswordChangeReq {
    private final String password;
    private final String email;

    public PasswordChangeReq(String password,
                             String email) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
