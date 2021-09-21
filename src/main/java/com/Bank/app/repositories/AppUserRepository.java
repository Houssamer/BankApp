package com.Bank.app.repositories;

import com.Bank.app.model.user.AppUser;
import com.Bank.app.model.user.AppUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface AppUserRepository
        extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
    @Query("select u from AppUser u where u.role=:x ")
    Collection<AppUser> findByRole(@Param("x") String role);
}
