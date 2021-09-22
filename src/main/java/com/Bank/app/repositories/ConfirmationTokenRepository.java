package com.Bank.app.repositories;

import com.Bank.app.model.ConfirmationToken;
import com.Bank.app.model.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface ConfirmationTokenRepository
        extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);
    Collection<ConfirmationToken> findConfirmationTokenByClient(Client client);
}
