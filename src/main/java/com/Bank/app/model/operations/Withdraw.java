package com.Bank.app.model.operations;

import com.Bank.app.model.Account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("W")
public class Withdraw extends Operations {
    public Withdraw(LocalDateTime date, double amount, Account account) {
        super(date, amount, account);
    }

    public Withdraw() {
        super();
    }
}
