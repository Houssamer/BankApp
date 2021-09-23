package com.Bank.app.services.accounts;

import com.Bank.app.model.Account;

import java.util.Collection;

public interface IAccountService {
    //retrieve data operations
    Account getAccount(String Number);
    Collection<Account> getUserAccounts(String email);

    //send data operations
    void addAccount(String email);
    void deleteAccount(String number);
}
