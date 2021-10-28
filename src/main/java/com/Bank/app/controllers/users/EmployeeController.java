package com.Bank.app.controllers.users;

import com.Bank.app.model.requests.PasswordChangeReq;
import com.Bank.app.model.requests.RegistrationRequest;
import com.Bank.app.model.user.Employee;
import com.Bank.app.services.users.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final AppUserService appUserService;

    @Autowired
    public EmployeeController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public Collection<Employee> getEmployees() {
        return appUserService.getEmployees();
    }

    @PostMapping()
    public Employee getEmployee(@RequestBody RegistrationRequest request) {
        return appUserService.getEmployee(request.getEmail());
    }

    @PostMapping("add")
    public void addEmployee(@RequestBody Employee employee) {
        appUserService.addEmployee(employee);
    }

    @DeleteMapping("delete")
    public void deleteEmployee(@RequestBody RegistrationRequest request) {

        appUserService.deleteEmployee(request.getEmail());
    }

    @PutMapping("update")
    public void updateEmployee(@RequestBody PasswordChangeReq req) {
        appUserService.updateEmployee(req.getEmail(), req.getPassword());
    }
}
