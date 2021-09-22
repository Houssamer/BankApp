package com.Bank.app.controllers.users;

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

    @DeleteMapping("delete/{email}")
    public void deleteManager(@PathVariable("email") String email) {
        appUserService.deleteManager(email);
    }

    @PutMapping("update/{email}")
    public void updateManager(@PathVariable("email") String email,
                              @RequestBody String password) {
        appUserService.updateManager(email, password);
    }
}
