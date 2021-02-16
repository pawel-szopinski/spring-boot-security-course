package com.example.demo.security;

import java.util.Collections;
import java.util.Set;

import static com.example.demo.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    ADMIN(Set.of(STUDENT_READ, STUDENT_WRITE, COURSE_WRITE, COURSE_READ)),
    ADMIN_TRAINEE(Set.of(STUDENT_READ, COURSE_READ)),
    STUDENT(Collections.emptySet());

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
