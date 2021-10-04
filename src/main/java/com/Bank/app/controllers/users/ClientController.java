package com.Bank.app.controllers.users;

import com.Bank.app.model.requests.PasswordChangeReq;
import com.Bank.app.model.requests.RegistrationRequest;
import com.Bank.app.model.user.Client;
import com.Bank.app.services.registration.RegistrationService;
import com.Bank.app.services.users.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/api/client")
public class ClientController {
    private final RegistrationService registrationService;
    private final AppUserService appUserService;

    @Autowired
    public ClientController(RegistrationService registrationService,
                            AppUserService appUserService) {
        this.registrationService = registrationService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public Collection<Client> getClients() {
        return appUserService.getClients();
    }

    @PostMapping
    public Client getClient(@RequestBody RegistrationRequest request) {
        return appUserService.getClient(request.getEmail());
    }

    @GetMapping("confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @GetMapping("enable")
    public void enableClient(@RequestBody RegistrationRequest request) {
        appUserService.enableClient(request.getEmail());
    }

    @PostMapping("add")
    public String addClient(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @DeleteMapping("delete")
    public void deleteClient(@RequestBody RegistrationRequest request) {
        appUserService.deleteClient(request.getEmail());
    }

    @PutMapping("update/")
    public void updateClient(@RequestBody PasswordChangeReq req) {
        appUserService.updateClient(req.getEmail(), req.getPassword());
    }

}
