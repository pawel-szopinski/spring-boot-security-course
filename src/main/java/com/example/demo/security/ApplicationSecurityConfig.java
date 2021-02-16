package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.demo.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        var user = User.builder()
                .username("student")
                .password("pass")
                .roles(STUDENT.name())
                .passwordEncoder(passwordEncoder::encode)
                .build();

        var admin = User.builder()
                .username("admin")
                .password("pass")
                .roles(ADMIN.name())
                .passwordEncoder(passwordEncoder::encode)
                .build();

        var adminTrainee = User.builder()
                .username("admin_trainee")
                .password("pass")
                .roles(ADMIN_TRAINEE.name())
                .passwordEncoder(passwordEncoder::encode)
                .build();

        return new InMemoryUserDetailsManager(admin, adminTrainee, user);
    }
}
