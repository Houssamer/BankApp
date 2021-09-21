package com.Bank.app.services.accounts;

import com.Bank.app.model.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class AccountService implements IAccountService {
    @Override
    public Account getAccount(String Number) {
        return null;
    }

    @Override
    public Collection<Account> getUserAccounts(String email) {
        return null;
    }

    @Override
    public void addAccount(Account account) {

    }

    @Override
    public void deleteAccount(String number) {

    }

    @Override
    public void updateAccount(String number, Account account) {

    }
}
