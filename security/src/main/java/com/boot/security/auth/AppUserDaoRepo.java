package com.boot.security.auth;

import com.boot.security.securityconf.AppRole;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository("fake")
public class AppUserDaoRepo implements AppUserDao {
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AppUserDaoRepo(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public Optional<AppUser> selecAppUserByUsername(String username) {
        return getAppUsers()
                .stream()
                .filter(appUser ->
                        username.equals(appUser.getUsername())

                ).findFirst();
    }

    private List<AppUser> getAppUsers() {
        List<AppUser> users = Lists.newArrayList(
                new AppUser("tuanuser",
                        passwordEncoder.encode("123"),
                        AppRole.STUDENT.getAuthorities(),
                        true,
                        true,
                        true, true
                )
                , new AppUser("tuanadmin",
                        passwordEncoder.encode("123"),
                        AppRole.ADMIN.getAuthorities(),
                        true,
                        true,
                        true, true
                ),
                new AppUser("tuanread",
                        passwordEncoder.encode("123"),
                        AppRole.ADMINTRAINEE.getAuthorities(),
                        true,
                        true,
                        true, true
                )
        );
        return users;
    }
}
