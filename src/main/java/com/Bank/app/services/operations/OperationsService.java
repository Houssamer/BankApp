package com.Bank.app.services.operations;

import com.Bank.app.model.Account;
import com.Bank.app.model.operations.Deposit;
import com.Bank.app.model.operations.Operations;
import com.Bank.app.model.operations.Transfer;
import com.Bank.app.model.operations.Withdraw;
import com.Bank.app.repositories.AccountRepository;
import com.Bank.app.repositories.OperationsRepository;
import com.Bank.app.services.accounts.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;


@Service
@Transactional
public class OperationsService implements IOperationsService{
    private final OperationsRepository<Operations> operationsRepository;
    private final OperationsRepository<Deposit> depositRepository;
    private final OperationsRepository<Transfer> transferRepository;
    private final OperationsRepository<Withdraw> withdrawRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Autowired
    public OperationsService(OperationsRepository<Operations> operationsRepository,
                             OperationsRepository<Deposit> depositRepository,
                             OperationsRepository<Transfer> transferRepository,
                             OperationsRepository<Withdraw> withdrawRepository,
                             AccountService accountService,
                             AccountRepository accountRepository) {

        this.operationsRepository = operationsRepository;
        this.depositRepository = depositRepository;
        this.transferRepository = transferRepository;
        this.withdrawRepository = withdrawRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @Override
    public Collection<Operations> accountOperations(String number) {
        Account account = accountService.getAccount(number);
        return operationsRepository.findAccountOperations(account);
    }

    @Override
    public void Deposit(String number, double amount) {
        Account account = accountService.getAccount(number);
        Deposit deposit = new Deposit(LocalDateTime.now(), amount, account);
        operationsRepository.save(deposit);
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    @Override
    public void Transfer(String accountNumber1,
                         String accountNumber2,
                         double amount) {

        Account account1 = accountService.getAccount(accountNumber1);
        Account account2 = accountService.getAccount(accountNumber2);
        if (account1.getBalance() < amount) {
            throw new IllegalStateException("not enough balance");
        }

        Transfer transfer = new Transfer(LocalDateTime.now(), amount, account1);
        account1.setBalance(account1.getBalance()-amount);
        Transfer transfer1 = new Transfer(LocalDateTime.now(), amount, account2);
        account2.setBalance(account2.getBalance()+amount);
        operationsRepository.save(transfer);
        operationsRepository.save(transfer1);
        accountRepository.save(account1);
        accountRepository.save(account2);
    }

    @Override
    public void Withdraw(String number, double amount) {
        Account account = accountService.getAccount(number);
        if (account.getBalance() < amount) {
            throw new IllegalStateException("not enough balance");
        }
        Withdraw withdraw = new Withdraw(LocalDateTime.now(), amount, account);
        operationsRepository.save(withdraw);
        account.setBalance(account.getBalance()-amount);
        accountRepository.save(account);
    }

    @Override
    public Collection<Deposit> accountDeposit(String number) {
        Account account = accountService.getAccount(number);
        return depositRepository.findAccountDeposit(account);
    }

    @Override
    public Collection<Transfer> accountTransfer(String number) {
        Account account = accountService.getAccount(number);
        return transferRepository.findAccountTransfer(account);
    }

    @Override
    public Collection<Withdraw> accountWithdraw(String number) {
        Account account = accountService.getAccount(number);
        return withdrawRepository.findAccountWithdraw(account);
    }
}
