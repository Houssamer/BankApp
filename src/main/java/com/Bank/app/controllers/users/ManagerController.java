package com.Bank.app.controllers.users;

import com.Bank.app.model.requests.LoginRequest;
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

    @GetMapping()
    public Manager getManagerById(@RequestParam("id") Long id) { return appUserService.getManagerById(id);}

    @GetMapping("all")
    public Collection<Manager> getManagers() {
        return appUserService.getManagers();
    }

    @PostMapping()
    public Manager getManager(@RequestBody LoginRequest request) {
        return appUserService.getManager(request.getEmail());
    }

    @PostMapping("add")
    public void addManager(@RequestBody Manager manager) {
        appUserService.addManager(manager);
    }

    @DeleteMapping("delete")
    public void deleteManager(@RequestParam("id") Long id) {
        appUserService.deleteManager(id);
    }

    @PutMapping("update")
    public void updateManager(@RequestBody PasswordChangeReq req) {
        appUserService.updateManager(req.getEmail(), req.getPassword());
    }
}
