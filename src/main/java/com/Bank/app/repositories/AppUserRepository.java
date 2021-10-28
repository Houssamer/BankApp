package com.Bank.app.repositories;

import com.Bank.app.model.user.AppUser;


import com.Bank.app.model.user.Client;
import com.Bank.app.model.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Collection;
import java.util.Optional;

    public interface AppUserRepository<T extends AppUser>
        extends JpaRepository<T, Long> {
    Optional<T> findByEmail(String email);
    @Query("select c from Client c where c.enabled=false")
    Collection<Client> findDisabledClients();
    @Query("select c from Client c where c.enabled=true")
    Collection<Client> findAllClients();
    @Query("select e from Employee e")
    Collection<Employee> findAllEmployees();
}
