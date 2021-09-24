package com.Bank.app.repositories;

import com.Bank.app.model.user.AppUser;


import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

    public interface AppUserRepository<T extends AppUser>
        extends JpaRepository<T, Long> {
    Optional<T> findByEmail(String email);
}
