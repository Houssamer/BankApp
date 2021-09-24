package com.Bank.app.model.operations;

import com.Bank.app.model.Account;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE_OP", length=1)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Deposit.class, name = "Deposit"),
        @JsonSubTypes.Type(value = Transfer.class, name = "Transfer"),
        @JsonSubTypes.Type(value = Withdraw.class, name = "Withdraw")
})
public abstract class Operations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private double amount;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ACCOUNT_NUMBER")
    private Account account;


    public Operations(LocalDateTime date, double amount, Account account) {
        this.date = date;
        this.amount = amount;
        this.account = account;
    }

    public Operations() {
        super();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
