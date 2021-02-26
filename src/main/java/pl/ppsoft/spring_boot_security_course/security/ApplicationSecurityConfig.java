package pl.ppsoft.spring_boot_security_course.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/*.html", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/*/students/**").hasAnyRole(
                ApplicationUserRole.ADMIN.name(), ApplicationUserRole.STUDENT.name())
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        var student = User.builder()
                .username("student")
                .password("pass")
                .passwordEncoder(passwordEncoder::encode)
                .authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
                .build();

        var admin = User.builder()
                .username("admin")
                .password("pass")
                .passwordEncoder(passwordEncoder::encode)
                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
                .build();

        var trainee = User.builder()
                .username("trainee")
                .password("pass")
                .passwordEncoder(passwordEncoder::encode)
                .authorities(ApplicationUserRole.TRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(admin, trainee, student);
    }
}
