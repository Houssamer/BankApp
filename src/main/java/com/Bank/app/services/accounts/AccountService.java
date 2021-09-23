package com.Bank.app.services.accounts;

import com.Bank.app.model.Account;
import com.Bank.app.model.user.Client;
import com.Bank.app.repositories.AccountRepository;
import com.Bank.app.repositories.AppUserRepository;
import com.Bank.app.services.users.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@Transactional
public class AccountService implements IAccountService {
    private final AccountRepository accountRepository;
    private final AppUserService appUserService;
    private final AppUserRepository<Client> appUserRepository;

    private static String GenerateAccountNumber() {
        String Num = "123456789";
        StringBuilder sb = new StringBuilder(24);
        for (int i = 0; i < 24; i++) {
            int index = (int) (Num.length() * Math.random());
            sb.append(Num.charAt(index));
        }

        return sb.toString();
    }
    @Autowired
    public AccountService(AccountRepository accountRepository,
                          AppUserService appUserService,
                          AppUserRepository<Client> appUserRepository) {
        this.accountRepository = accountRepository;
        this.appUserService = appUserService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public Account getAccount(String number) {
        return accountRepository.findById(number)
                .orElseThrow(() -> new IllegalStateException("account not found"));
    }

    @Override
    public Collection<Account> getUserAccounts(String email) {
        Client client = appUserService.getClient(email);
        return accountRepository.getAccountsByClient(client);
    }

    @Override
    public void addAccount(String email) {
        String accountNumber = GenerateAccountNumber();
        boolean accountExists = accountRepository.findById(accountNumber)
                .isPresent();
        if (accountExists) {
            throw new IllegalStateException("account already exist");
        }
        Client client = appUserService.getClient(email);
        Account account = new Account(
                accountNumber,
                0,
                LocalDateTime.now(),
                client
        );
        client.getAccounts().add(account);
        accountRepository.save(account);
        appUserRepository.save(client);
    }

    @Override
    public void deleteAccount(String number) {
        Account account = accountRepository.findById(number)
                .orElseThrow(() -> new IllegalStateException("Account not found"));
        accountRepository.delete(account);
    }

}
