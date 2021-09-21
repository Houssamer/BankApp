package com.Bank.app.model.operations;

import com.Bank.app.model.Account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("W")
public class Withdrawl extends Operations {
    public Withdrawl(Account account) {
        super(account);
    }
    public Withdrawl() {
        super();
    }
}
