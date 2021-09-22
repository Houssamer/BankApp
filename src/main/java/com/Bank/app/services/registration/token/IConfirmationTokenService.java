package com.Bank.app.services.registration.token;

import com.Bank.app.model.ConfirmationToken;
import com.Bank.app.model.user.Client;

import java.util.Optional;

public interface IConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken token);
    Optional<ConfirmationToken> getToken(String token);
    void deleteToken(Client client);
}
