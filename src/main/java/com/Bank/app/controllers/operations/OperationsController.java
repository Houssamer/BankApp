package com.Bank.app.controllers.operations;

import com.Bank.app.model.operations.Deposit;
import com.Bank.app.model.operations.Operations;
import com.Bank.app.model.operations.Transfer;
import com.Bank.app.model.operations.Withdraw;
import com.Bank.app.model.requests.OperationRequest;
import com.Bank.app.services.operations.OperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@RestController
@RequestMapping("/api/operations")
public class OperationsController {
    private final OperationsService operationsService;

    @Autowired
    public OperationsController(OperationsService operationsService) {
        this.operationsService = operationsService;
    }

    @PostMapping
    Collection<Operations> getAccountOperations(
            @RequestBody OperationRequest request) {
        return operationsService
                .accountOperations(request.getAccountNumber1());
    }

    @PostMapping("/account/deposit")
    Collection<Deposit> getAccountDeposit(
            @RequestBody OperationRequest request) {
        return operationsService
                .accountDeposit(request.getAccountNumber1());
    }

    @PostMapping("/account/transfer")
    Collection<Transfer> getAccountTransfer(
            @RequestBody OperationRequest request) {
        return operationsService
                .accountTransfer(request.getAccountNumber1());
    }

    @PostMapping("/account/withdraw")
    Collection<Withdraw> getAccountWithdraw(
            @RequestBody OperationRequest request) {
        return operationsService
                .accountWithdraw(request.getAccountNumber1());
    }

    @PostMapping("/deposit")
    void deposit(@RequestBody OperationRequest request) {
        operationsService.Deposit(request.getAccountNumber1(), request.getAmount());
    }

    @PostMapping("/transfer")
    void transfer(@RequestBody OperationRequest request) {
        operationsService.Transfer(request.getAccountNumber1(),
                request.getAccountNumber2(),
                request.getAmount());
    }

    @PostMapping("/withdraw")
    void withdraw(@RequestBody OperationRequest request) {
        operationsService.Withdraw(request.getAccountNumber1(), request.getAmount());
    }
}
