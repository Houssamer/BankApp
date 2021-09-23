package com.Bank.app.model.requests;

public class OperationRequest {
    private final String number;
    private final double amount;

    public OperationRequest(String number,
                            double amount) {
        this.number = number;
        this.amount = amount;
    }

    public String getNumber() {
        return number;
    }

    public double getAmount() {
        return amount;
    }
}
