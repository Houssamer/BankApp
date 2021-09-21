package com.Bank.app.model;

import com.Bank.app.model.operations.Operations;
import com.Bank.app.model.user.Client;
import io.micrometer.core.lang.Nullable;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Account {
    @Id
    private String number;
    private double balance;
    @ManyToOne
    @JoinColumn(name="CODE_CLIENT")
    private Client client;
    @OneToMany(mappedBy = "account")
    private Collection<Operations> operations;
    public Account(String number,
                   double balance,
                   Client client,
                   Collection<Operations> operations) {

        this.number = number;
        this.balance = balance;
        this.client = client;
        this.operations = operations;
    }

    public Account() {
        super();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Collection<Operations> getOperations() {
        return operations;
    }

    public void setOperations(Collection<Operations> operations) {
        this.operations = operations;
    }
}
