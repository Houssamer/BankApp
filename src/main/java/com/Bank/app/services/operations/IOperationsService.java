package com.Bank.app.services.operations;

import com.Bank.app.model.operations.Operations;
import org.springframework.data.domain.Page;

public interface IOperationsService {
    Page<Operations> listOperations(String number, int page, int size);
    void Deposit(String number, double amount);
    void Transfer(String number, double amount);
    void Withdraw(String number, double amount);
}
