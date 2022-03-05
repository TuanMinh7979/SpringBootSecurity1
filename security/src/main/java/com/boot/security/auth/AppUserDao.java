package com.boot.security.auth;

import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AppUserDao {
    Optional<AppUser> selecAppUserByUsername(String username);
}
