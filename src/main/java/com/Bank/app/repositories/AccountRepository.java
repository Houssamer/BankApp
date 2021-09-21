package com.Bank.app.repositories;

import com.Bank.app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AccountRepository
        extends JpaRepository<Account, String> {
    Collection<Account> getAccountByClient_Username();

}
