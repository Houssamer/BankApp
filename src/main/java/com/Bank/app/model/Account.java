package com.Bank.app.model;

import com.Bank.app.model.operations.Operations;
import com.Bank.app.model.user.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.micrometer.core.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
public class Account {
    @Id
    private String number;
    private double balance;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name="CODE_CLIENT")
    @JsonBackReference
    private Client client;
    @JsonManagedReference
    @OneToMany(mappedBy = "account")
    private Collection<Operations> operations;

    public Account(String number,
                   double balance,
                   LocalDateTime createdAt,
                   Client client) {

        this.number = number;
        this.balance = balance;
        this.createdAt = createdAt;
        this.client = client;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
