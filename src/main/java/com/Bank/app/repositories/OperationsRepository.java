package com.Bank.app.repositories;

import com.Bank.app.model.Account;
import com.Bank.app.model.operations.Deposit;
import com.Bank.app.model.operations.Operations;
import com.Bank.app.model.operations.Transfer;
import com.Bank.app.model.operations.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;


public interface OperationsRepository<T extends Operations>
        extends JpaRepository<T, Long> {
    @Query("select o from Operations o where o.account=:x order by o.date desc")
    Collection<Operations> findAccountOperations(@Param("x") Account account);
    @Query("select d from Deposit d where d.account=:x order by d.date desc")
    Collection<Deposit> findAccountDeposit(@Param("x") Account account);
    @Query("select t from Transfer t where t.account=:x order by t.date desc ")
    Collection<Transfer> findAccountTransfer(@Param("x") Account account);
    @Query("select w from Withdraw w where w.account=:x order by w.date desc")
    Collection<Withdraw> findAccountWithdraw(@Param("x") Account account);
}
