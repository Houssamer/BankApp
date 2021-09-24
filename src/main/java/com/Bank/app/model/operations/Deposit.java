package com.Bank.app.model.operations;

import com.Bank.app.model.Account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("D")
public class Deposit extends Operations {

    public Deposit(LocalDateTime date, double amount, Account account) {
        super(date, amount, account);
    }

    public Deposit() {
        super();
    }

}
