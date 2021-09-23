package com.Bank.app.services.registration;

import com.Bank.app.model.requests.RegistrationRequest;

public interface IRegistrationService {
    String register(RegistrationRequest request);
    String confirmToken(String token);
}
