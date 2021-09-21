package com.Bank.app.services.registration.token;

import com.Bank.app.model.ConfirmationToken;
import com.Bank.app.repositories.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfirmationTokenService implements IConfirmationTokenService{
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public ConfirmationTokenService(
            ConfirmationTokenRepository confirmationTokenRepository) {

        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    @Override
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }
}
