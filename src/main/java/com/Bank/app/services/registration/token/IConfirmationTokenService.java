package com.Bank.app.services.registration.token;

import com.Bank.app.model.ConfirmationToken;

import java.util.Optional;

public interface IConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken token);
    Optional<ConfirmationToken> getToken(String token);
}
