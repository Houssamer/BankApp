package com.Bank.app.model.operations;

import com.Bank.app.model.Account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("D")
public class Deposit extends Operations {

    public Deposit(Account account) {
        super(account);
    }

    public Deposit() {
        super();
    }
}
