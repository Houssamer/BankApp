package com.Bank.app.model.operations;

import com.Bank.app.model.Account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("T")
public class Transfer extends Operations {
    public Transfer(Account account) {
        super(account);
    }
    public Transfer() {
        super();
    }
}
