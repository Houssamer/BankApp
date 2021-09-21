package com.Bank.app.repositories;

import com.Bank.app.model.operations.Operations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OperationsRepository
        extends JpaRepository<Operations, Long> {
    @Query("select o from Operations o where o.account.number=:x order by o.date desc ")
    Page<Operations> listOperations(@Param("x") String number, Pageable pageable);
}
