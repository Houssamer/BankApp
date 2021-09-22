package com.Bank.app.controllers.users;

import com.Bank.app.model.RegistrationRequest;
import com.Bank.app.model.user.Client;
import com.Bank.app.services.registration.RegistrationService;
import com.Bank.app.services.users.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

// TODO: Commit the changes and push into github

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

    @GetMapping("{email}")
    public Client getClient(@PathVariable("email") String email) {
        return appUserService.getClient(email);
    }

    @GetMapping("confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @GetMapping("enable/{email}")
    public void enableClient(@PathVariable("email") String email) {
        appUserService.enableClient(email);
    }

    @PostMapping("add")
    public String addClient(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @DeleteMapping("delete/{email}")
    public void deleteClient(@PathVariable("email") String email) {
        appUserService.deleteClient(email);
    }

    @PutMapping("update/{email}")
    public void updateClient(@PathVariable("email") String email,
                             @RequestBody String password) {
        appUserService.updateClient(email, password);
    }

}
