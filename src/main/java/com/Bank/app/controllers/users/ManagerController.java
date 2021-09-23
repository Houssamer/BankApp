package com.Bank.app.controllers.users;

import com.Bank.app.model.requests.PasswordChangeReq;
import com.Bank.app.model.requests.RegistrationRequest;
import com.Bank.app.model.user.Manager;
import com.Bank.app.services.users.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {
    private final AppUserService appUserService;

    @Autowired
    public ManagerController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("all")
    public Collection<Manager> getManagers() {
        return appUserService.getManagers();
    }

    @GetMapping("{email}")
    public Manager getManager(@PathVariable("email") String email) {
        return appUserService.getManager(email);
    }

    @PostMapping("add")
    public void addManager(@RequestBody Manager manager) {
        appUserService.addManager(manager);
    }

    @DeleteMapping("delete")
    public void deleteManager(@RequestBody RegistrationRequest request) {
        appUserService.deleteManager(request.getEmail());
    }

    @PutMapping("update")
    public void updateManager(@RequestBody PasswordChangeReq req) {
        appUserService.updateManager(req.getEmail(), req.getPassword());
    }
}
