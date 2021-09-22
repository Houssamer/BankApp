package com.Bank.app.repositories;

import com.Bank.app.model.Account;
import com.Bank.app.model.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AccountRepository
        extends JpaRepository<Account, String> {
    Collection<Account> getAccountsByClient(Client client);

}
