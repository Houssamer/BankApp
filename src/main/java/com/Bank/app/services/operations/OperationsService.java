package com.Bank.app.services.operations;

import com.Bank.app.model.operations.Operations;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//TODO: commit changes and push to github

@Service
@Transactional
public class OperationsService implements IOperationsService{
    @Override
    public Page<Operations> listOperations(String number, int page, int size) {
        return null;
    }

    @Override
    public void Deposit(String number, double amount) {

    }

    @Override
    public void Transfer(String number, double amount) {

    }

    @Override
    public void Withdraw(String number, double amount) {

    }
}
