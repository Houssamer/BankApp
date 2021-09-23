package com.Bank.app.controllers.account;

import com.Bank.app.model.Account;
import com.Bank.app.model.requests.AccountRequests;
import com.Bank.app.services.accounts.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping()
    public Account getAccount(@RequestBody AccountRequests req) {
        return accountService.getAccount(req.getNumber());
    }

    @PostMapping("user")
    public Collection<Account> getUserAccounts(@RequestBody AccountRequests req) {
        return accountService.getUserAccounts(req.getUserEmail());
    }

    @PostMapping("add")
    public void addAccount(@RequestBody AccountRequests req) {

        accountService.addAccount(req.getUserEmail());
    }

    @DeleteMapping("delete")
    public void deleteAccount(@RequestBody AccountRequests req) {
        accountService.deleteAccount(req.getNumber());
    }
}
