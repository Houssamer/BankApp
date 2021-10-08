package com.Bank.app.services.users;

import com.Bank.app.model.ConfirmationToken;
import com.Bank.app.model.requests.PasswordChangeReq;
import com.Bank.app.model.user.*;
import com.Bank.app.repositories.AppUserRepository;
import com.Bank.app.repositories.ConfirmationTokenRepository;
import com.Bank.app.services.registration.email.EmailSender;
import com.Bank.app.services.registration.token.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Service
@Transactional
public class AppUserService implements IAppUserService, UserDetailsService {
    private final AppUserRepository<AppUser> appUserRepository;
    private final AppUserRepository<Client> clientRepository;
    private final AppUserRepository<Employee> employeeRepository;
    private final AppUserRepository<Manager> managerRepository;
    private final AppUserRepository<SysAdmin> sysAdminRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSender emailSender;

    @Autowired
    public AppUserService(AppUserRepository<AppUser> appUserRepository,
                          AppUserRepository<Client> clientRepository,
                          AppUserRepository<Employee> employeeRepository,
                          AppUserRepository<Manager> managerRepository,
                          AppUserRepository<SysAdmin> sysAdminRepository,
                          ConfirmationTokenRepository confirmationTokenRepository,
                          ConfirmationTokenService confirmationTokenService,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          EmailSender emailSender) {

        this.appUserRepository = appUserRepository;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.managerRepository = managerRepository;
        this.sysAdminRepository = sysAdminRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.confirmationTokenService = confirmationTokenService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailSender = emailSender;
    }

    @Override
    public Client getClient(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Client not found"));
    }

    @Override
    public Employee getEmployee(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Employee not found"));
    }

    @Override
    public Manager getManager(String email) {
        return managerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Manager not found"));
    }

    @Override
    public SysAdmin getSysAdmin(String email) {
        return sysAdminRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("SysAdmin not found"));
    }

    @Override
    public Collection<AppUser> getAllUsers() {

        return appUserRepository.findAll();
    }

    @Override
    public Collection<Client> getClients() {

        return clientRepository.findAll();
    }

    @Override
    public Collection<Client> getDisabledClients() {
        return appUserRepository.findDisabledClients();
    }

    @Override
    public Collection<Employee> getEmployees() {

        return employeeRepository.findAll();
    }

    @Override
    public Collection<Manager> getManagers() {

        return managerRepository.findAll();
    }

    @Override
    public Collection<SysAdmin> getSysAdmins() {
        return sysAdminRepository.findAll();
    }

    @Override
    public String addClient(Client client) {
        boolean clientExists =
                clientRepository.findByEmail(client.getUsername()).isPresent();

        if (clientExists) {
            Client clientA =
                    clientRepository.findByEmail(client.getUsername()).get();
            if (clientA.getLocked()) {
                String token = UUID.randomUUID().toString();
                ConfirmationToken confirmationToken =
                        new ConfirmationToken(
                                token,
                                LocalDateTime.now(),
                                LocalDateTime.now().plusMinutes(15),
                                clientA
                        );
                confirmationTokenRepository.save(confirmationToken);
                String link = "http://localhost:8080/api/client/confirm?token="+token;
                emailSender.send(
                        client.getUsername(),
                        buildEmail(client.getFirstName(), link)
                );

                return token;
            }
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword =
                bCryptPasswordEncoder.encode(client.getPassword());
        client.setPassword(encodedPassword);
        clientRepository.save(client);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken =
                new ConfirmationToken(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15),
                        client
                );
        confirmationTokenRepository.save(confirmationToken);

        String link = "http://localhost:8080/api/client/confirm?token="+token;
        emailSender.send(
                client.getUsername(),
                buildEmail(client.getFirstName(), link)
             );
        return token;
    }

    @Override
    public void deleteClient(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        confirmationTokenService.deleteToken(client);
        clientRepository.delete(client);
    }

    @Override
    public void updateClient(String email, String password) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        if (client.getPassword()
                .equals(encodedPassword)) {
            throw new IllegalStateException("The password is the same as one of your previous password");
        }
        client.setPassword(encodedPassword);
        clientRepository.save(client);
    }

    @Override
    public void addEmployee(Employee employee) {
        boolean employeeExists =
                employeeRepository.findByEmail(employee.getUsername())
                        .isPresent();

        if (employeeExists) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword =
                bCryptPasswordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(String email) {
        Employee employee = employeeRepository
                .findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Employee not found"));
        employeeRepository.delete(employee);
    }

    @Override
    public void updateEmployee(String email, String password) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Employee not found"));
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        if (employee.getPassword().equals(encodedPassword)) {
            throw new IllegalStateException("the password is the same as one of your previous password");
        }

        employee.setPassword(encodedPassword);
        employeeRepository.save(employee);
    }

    @Override
    public void addManager(Manager manager) {
        boolean managerExist = managerRepository.findByEmail(manager.getUsername())
                .isPresent();
        if (managerExist) {
            throw new IllegalStateException("email Already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(manager.getPassword());
        manager.setPassword(encodedPassword);
        managerRepository.save(manager);
    }

    @Override
    public void deleteManager(String email) {
        Manager manager = managerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Manager not found"));
        managerRepository.delete(manager);
    }

    @Override
    public void updateManager(String email, String password) {
        Manager manager = managerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Employee not found"));
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        if (manager.getPassword().equals(encodedPassword)) {
            throw new IllegalStateException("the password is the same as one of your previous passwords");
        }
        manager.setPassword(encodedPassword);
        managerRepository.save(manager);
    }

    @Override
    public void addSysAdmin(SysAdmin sysAdmin) {
        boolean AdminExist = sysAdminRepository
                .findByEmail(sysAdmin.getUsername())
                .isPresent();
        if (AdminExist) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(sysAdmin.getPassword());
        sysAdmin.setPassword(encodedPassword);
        sysAdminRepository.save(sysAdmin);
    }

    @Override
    public void deleteSysAdmin(String email) {
        SysAdmin admin = sysAdminRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Admin not found"));
        sysAdminRepository.delete(admin);
    }

    @Override
    public void updateSysAdmin(String email, String password) {
        SysAdmin admin = sysAdminRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Admin not found"));
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        if (admin.getPassword().equals(encodedPassword)) {
            throw new IllegalStateException("the password is the same as one of your previous password");
        }
        admin.setPassword(encodedPassword);
        sysAdminRepository.save(admin);
    }

    @Override
    public void unlockClient(String email) {
        Client user = clientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        user.setLocked(false);
        clientRepository.save(user);
    }
    @Override
    public void enableClient(String email) {
        Client client = (Client) clientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        client.setEnabled(true);
        clientRepository.save(client);
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return appUserRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalStateException("user not found"));
    }
}
