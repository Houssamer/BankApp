package com.Bank.app.model.user;

import com.Bank.app.model.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@DiscriminatorValue(value = "C")
public class Client extends AppUser {
    @Transient
    private AppUserRole role = AppUserRole.Client;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Collection<Account> accounts;
    private Boolean locked = true;
    private Boolean enabled = false;

    public Client(String firstName,
                  String lastName,
                  String email,
                  String password) {

        super(firstName, lastName, email, password);
    }

    public Client() {
        super();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(this.role.name());
        return Collections.singletonList(authority);
    }
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public AppUserRole getRole() {
        return role;
    }

    public void setRole(AppUserRole role) {
        this.role = role;
    }

    public Collection<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Collection<Account> accounts) {
        this.accounts = accounts;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
