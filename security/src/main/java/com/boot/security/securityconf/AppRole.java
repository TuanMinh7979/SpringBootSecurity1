package com.boot.security.securityconf;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.boot.security.securityconf.AppUserPermission.*;

public enum AppRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ,
            STUDENT_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ));

    private AppRole(Set<AppUserPermission> permission) {
        this.permission = permission;
    }

    private final Set<AppUserPermission> permission;

    public Set<AppUserPermission> getPermission() {
        return permission;
    }

    public Set<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermission()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//        System.out.println("---TEST");
//        for (GrantedAuthority g : permissions) {
//            System.out.println(g.toString());
//        }
        return permissions;
    }

}
