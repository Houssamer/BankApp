package com.Bank.app.security;

import com.Bank.app.jwt.JwtUsernamePasswordFilter;
import com.Bank.app.jwt.JwtVerifier;
import com.Bank.app.model.user.AppUserRole;
import com.Bank.app.services.users.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityConfig(AppUserService appUserService,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserService = appUserService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
               .csrf()
                    .disable()
               .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .addFilter(new JwtUsernamePasswordFilter(authenticationManagerBean()))
               .authorizeRequests()
                    .antMatchers("/")
                        .permitAll()
                    .antMatchers("/api/client/add")
                        .permitAll()
                    .antMatchers("/api/client/confirm")
                        .permitAll();
        http
                .csrf()
                    .disable()
                .addFilterBefore(new JwtVerifier(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/client/")
                    .hasAnyAuthority(AppUserRole.Client.name())
                .antMatchers("/api/client/update")
                    .hasAnyAuthority(AppUserRole.Client.name())
                .antMatchers("/api/client/*")
                    .hasAnyAuthority(AppUserRole.Employee.name())
                .antMatchers("/api/employee/*")
                    .hasAnyAuthority(AppUserRole.Manager.name())
                .antMatchers("/api/manager/*")
                    .hasAnyAuthority(AppUserRole.SysAdmin.name())
                .antMatchers("/api/account/add")
                    .hasAnyAuthority(AppUserRole.Client.name())
                .antMatchers("/api/account/*")
                    .hasAnyAuthority(AppUserRole.Employee.name())
                .antMatchers("/api/operations/transfer", "/api/operations/withdraw")
                    .hasAnyAuthority(
                            AppUserRole.Client.name())
                .antMatchers("/api/operations/account/*")
                    .hasAnyAuthority(AppUserRole.Client.name())
                .antMatchers("/api/operations/*")
                    .hasAnyAuthority(AppUserRole.Employee.name())
                .anyRequest()
                .authenticated();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }

    @Override
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }
}
