package pl.ppsoft.spring_boot_security_course.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.ppsoft.spring_boot_security_course.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    ADMIN(Set.of(STUDENT_READ, STUDENT_WRITE, COURSE_WRITE, COURSE_READ)),
    TRAINEE(Set.of(STUDENT_READ, COURSE_READ)),
    STUDENT(Collections.emptySet());

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<GrantedAuthority> getGrantedAuthorities() {
        Set<GrantedAuthority> authorities = getPermissions().stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toSet());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
