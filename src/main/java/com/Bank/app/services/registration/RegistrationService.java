package com.Bank.app.services.registration;

import com.Bank.app.model.ConfirmationToken;
import com.Bank.app.model.requests.RegistrationRequest;
import com.Bank.app.model.user.Client;
import com.Bank.app.services.registration.token.ConfirmationTokenService;
import com.Bank.app.services.users.AppUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class RegistrationService implements IRegistrationService {
    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public RegistrationService(AppUserService appUserService,
                               ConfirmationTokenService confirmationTokenService) {
        this.appUserService = appUserService;
        this.confirmationTokenService = confirmationTokenService;
    }

    @Override
    public String register(RegistrationRequest request) {
        String token = appUserService.addClient(
                new Client(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword()
                )
        );
        return token;
    }

    @Override
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));
        if (confirmationToken.getConfirmedAt()!=null) {
            throw new IllegalStateException("Email already confirmed");
        }
        LocalDateTime expiresAt = confirmationToken.getExpiresAt();
        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        appUserService.unlockClient(
                confirmationToken.getClient().getUsername()
        );
        return "confirmed";
    }
}
