package com.Bank.app.services.users;

import com.Bank.app.model.user.*;

import java.util.Collection;

public interface IAppUserService {
    //retrieve data operations
    AppUser getUser(String email);
    Collection<AppUser> getAllUsers();
    Collection<AppUser> getUsers();
    Collection<AppUser> getEmployees();
    Collection<AppUser> getManagers();
    //send data operations
    //clients
    String addClient(Client client);
    void deleteClient(String email);
    void updateClient(String email, String password);
    void unlockClient(String email);
    void enableClient(String email);
    //employees
    void addEmployee(Employee employee);
    void deleteEmployee(String email);
    void updateEmployee(String email, String password);
    //managers
    void addManager(Manager manager);
    void deleteManager(String email);
    void updateManager(String email, String password);
    //SysAdmin
    void addSysAdmin(SysAdmin sysAdmin);
    void deleteSysAdmin(String email);
    void updateSysAdmin(String email, String password);

}
