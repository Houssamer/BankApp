package com.Bank.app.controllers;

import com.Bank.app.model.requests.LoginRequest;
import com.Bank.app.services.users.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
public class Role {
    private AppUserService appUserService;

    @Autowired
    public Role(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping()
    public String getRole(@RequestBody LoginRequest request) {
        return appUserService.getUserRole(request.getEmail());
    }
}
