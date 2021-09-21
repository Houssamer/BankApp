package com.Bank.app.services.users;

import com.Bank.app.model.ConfirmationToken;
import com.Bank.app.model.user.*;
import com.Bank.app.repositories.AppUserRepository;
import com.Bank.app.repositories.ConfirmationTokenRepository;
import com.Bank.app.services.registration.email.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Service
@Transactional
public class AppUserService implements IAppUserService{
    private final AppUserRepository appUserRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSender emailSender;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository,
                          ConfirmationTokenRepository confirmationTokenRepository,
                          EmailSender emailSender,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailSender = emailSender;
    }

    @Override
    public AppUser getUser(String email) {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("No Account with email: "+email));
    }

    @Override
    public Collection<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public Collection<AppUser> getUsers() {
        return appUserRepository.findByRole(AppUserRole.Client.name());
    }

    @Override
    public Collection<AppUser> getEmployees() {
        return appUserRepository.findByRole(AppUserRole.Employee.name());
    }

    @Override
    public Collection<AppUser> getManagers() {
        return appUserRepository.findByRole(AppUserRole.Manager.name());
    }

    @Override
    public String addClient(Client client) {
        boolean clientExists =
                appUserRepository.findByEmail(client.getUsername()).isPresent();

        if (clientExists) {
            Client clientA = (Client)
                    appUserRepository.findByEmail(client.getUsername()).get();
            if (!clientA.isEnabled()) {
                String token = UUID.randomUUID().toString();
                ConfirmationToken confirmationToken =
                        new ConfirmationToken(
                                token,
                                LocalDateTime.now(),
                                LocalDateTime.now().plusMinutes(15),
                                clientA
                        );
                String link = "http://localhost:8080/confirm?token="+token;
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
        appUserRepository.save(client);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken =
                new ConfirmationToken(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15),
                        client
                );
        confirmationTokenRepository.save(confirmationToken);

        String link = "http://localhost:8080/api/confirm?token="+token;
        emailSender.send(
                client.getUsername(),
                buildEmail(client.getFirstName(), link)
             );
        return token;
    }

    @Override
    public void deleteClient(String email) {
        Client client = (Client) appUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        appUserRepository.delete(client);
    }

    @Override
    public void updateClient(String email, String password) {
        Client client = (Client) appUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        if (client.getPassword()
                .equals(bCryptPasswordEncoder.encode(password))) {
            throw new IllegalStateException("The password is th e same as one of your previous password");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        client.setPassword(encodedPassword);
        appUserRepository.save(client);
    }

    @Override
    public void addEmployee(Employee employee) {
        boolean employeeExists =
                appUserRepository.findByEmail(employee.getUsername())
                        .isPresent();

        if (employeeExists) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword =
                bCryptPasswordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
        appUserRepository.save(employee);
    }

    @Override
    public void deleteEmployee(String email) {
        Employee employee = (Employee) appUserRepository
                .findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Employee not found"));
        appUserRepository.delete(employee);
    }

    @Override
    public void updateEmployee(String email, String password) {
        Employee employee = (Employee) appUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Employee not found"));
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        if (employee.getPassword().equals(encodedPassword)) {
            throw new IllegalStateException("the password is the same as one of your previous password");
        }

        employee.setPassword(encodedPassword);
        appUserRepository.save(employee);
    }

    @Override
    public void addManager(Manager manager) {
        boolean managerExist = appUserRepository.findByEmail(manager.getUsername())
                .isPresent();
        if (managerExist) {
            throw new IllegalStateException("email Already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(manager.getPassword());
        manager.setPassword(encodedPassword);
        appUserRepository.save(manager);
    }

    @Override
    public void deleteManager(String email) {
        Manager manager = (Manager) appUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Manager not found"));
        appUserRepository.delete(manager);
    }

    @Override
    public void updateManager(String email, String password) {
        Manager manager = (Manager) appUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Employee not found"));
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        if (manager.getPassword().equals(encodedPassword)) {
            throw new IllegalStateException("the password is the same as one of your previous passwords");
        }
        manager.setPassword(encodedPassword);
        appUserRepository.save(manager);
    }

    @Override
    public void addSysAdmin(SysAdmin sysAdmin) {
        boolean AdminExist = appUserRepository
                .findByEmail(sysAdmin.getUsername())
                .isPresent();
        if (AdminExist) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(sysAdmin.getPassword());
        sysAdmin.setPassword(encodedPassword);
        appUserRepository.save(sysAdmin);
    }

    @Override
    public void deleteSysAdmin(String email) {
        SysAdmin admin = (SysAdmin) appUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Admin not found"));
        appUserRepository.delete(admin);
    }

    @Override
    public void updateSysAdmin(String email, String password) {
        SysAdmin admin = (SysAdmin) appUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Admin not found"));
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        if (admin.getPassword().equals(encodedPassword)) {
            throw new IllegalStateException("the password is the same as one of your previous password");
        }
        admin.setPassword(encodedPassword);
        appUserRepository.save(admin);
    }

    @Override
    public void unlockClient(String email) {
        Client user = (Client) appUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        user.setLocked(false);
        appUserRepository.save(user);
    }
    @Override
    public void enableClient(String email) {
        Client client = (Client) appUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        client.setEnabled(true);
        appUserRepository.save(client);
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
}
