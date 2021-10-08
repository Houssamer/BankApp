package com.Bank.app.services.users;


import com.Bank.app.model.user.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

public interface IAppUserService {
    //retrieve data operations
    //client
    Client getClient(String email);
    Collection<Client> getClients();
    Collection<Client> getDisabledClients();
    //employee
    Employee getEmployee(String email);
    Collection<Employee> getEmployees();
    //manager
    Manager getManager(String email);
    Collection<Manager> getManagers();
    //admin
    SysAdmin getSysAdmin(String email);
    Collection<SysAdmin> getSysAdmins();
    //all
    Collection<AppUser> getAllUsers();
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
