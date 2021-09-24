package com.Bank.app.services.operations;

import com.Bank.app.model.operations.Deposit;
import com.Bank.app.model.operations.Operations;
import com.Bank.app.model.operations.Transfer;
import com.Bank.app.model.operations.Withdraw;

import java.util.Collection;

public interface IOperationsService {
    Collection<Operations> accountOperations(String number);
    void Deposit(String number, double amount);
    void Transfer(String accountNumber1, String accountNumber2, double amount);
    void Withdraw(String number, double amount);
    Collection<Deposit> accountDeposit(String number);
    Collection<Transfer> accountTransfer(String number);
    Collection<Withdraw> accountWithdraw(String number);
}
