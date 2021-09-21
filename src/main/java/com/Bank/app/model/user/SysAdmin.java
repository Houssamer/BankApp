package com.Bank.app.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.Collections;

@Entity
@DiscriminatorValue("S")
public class SysAdmin extends AppUser {
    @Transient
    private AppUserRole role = AppUserRole.SysAdmin;

    public SysAdmin(String firstName,
                    String lastName,
                    String email,
                    String password) {
        super(firstName, lastName, email, password);
    }

    public SysAdmin() {
        super();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(this.role.name());
        return Collections.singletonList(authority);
    }

    public AppUserRole getRole() {
        return role;
    }

    public void setRole(AppUserRole role) {
        this.role = role;
    }
}
