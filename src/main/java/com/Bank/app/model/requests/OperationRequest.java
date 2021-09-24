package com.Bank.app.model.requests;

public class OperationRequest {

    private final String accountNumber1;
    private final String accountNumber2;
    private final double amount;

    public OperationRequest(String accountNumber1,
                            String accountNumber2,
                            double amount) {
        this.accountNumber1 = accountNumber1;
        this.accountNumber2 = accountNumber2;
        this.amount = amount;
    }

    public String getAccountNumber1() {
        return accountNumber1;
    }

    public String getAccountNumber2() {
        return accountNumber2;
    }

    public double getAmount() {
        return amount;
    }
}
