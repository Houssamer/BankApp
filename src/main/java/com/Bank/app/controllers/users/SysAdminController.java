package com.Bank.app.controllers.users;

import com.Bank.app.model.requests.PasswordChangeReq;
import com.Bank.app.model.requests.RegistrationRequest;
import com.Bank.app.model.user.SysAdmin;
import com.Bank.app.services.users.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/admin")
public class SysAdminController {
    private final AppUserService appUserService;

    @Autowired
    public SysAdminController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("all")
    public Collection<SysAdmin> getSysAdmins() {
        return appUserService.getSysAdmins();
    }

    @GetMapping("{email}")
    public SysAdmin getSysAdmin(@PathVariable("email") String email) {
        return appUserService.getSysAdmin(email);
    }

    @PostMapping("add")
    public void addSysAdmin(@RequestBody SysAdmin sysAdmin) {
        appUserService.addSysAdmin(sysAdmin);
    }

    @DeleteMapping("delete")
    public void deleteSysAdmin(@RequestBody RegistrationRequest request) {

        appUserService.deleteSysAdmin(request.getEmail());
    }

    @PutMapping("update")
    public void updateSysAdmin(@RequestBody PasswordChangeReq req) {
        appUserService.updateSysAdmin(req.getEmail(), req.getPassword());
    }
}
