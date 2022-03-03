package com.boot.security.securityconf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {
    // Nếu auto từ 1
    // method thì
    // cần inject
    // theo setter
    // hay constructor

    private final PasswordEncoder passwordEncoder;

    // neu tao 1 bean trong 1 config class(khong chi dinh ten bean thi ten phuong
    // thuc se lam ten bean va chi co 1 bean mang type la class do) thi co the
    // inject no
    // thong qua constructor
    @Autowired
    public SecurityConf(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/", "/index", "/css/*", "/js/*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    // Bug here: Không có lỗi do trong IOC và trong file config chưa có bất kỳ
    // userDetailsService nào
    // TẠO BEAN USERDETAILSSERVICE (có thể nào bằng @Service anno)
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails tuanUser = User.builder()
                .username("tuan")
                .password(passwordEncoder.encode("123"))
                .roles("STUDENT")
                .build();
        return new InMemoryUserDetailsManager(
                tuanUser);
    }
    // Bug here: sẽ bị lỗi no password encoder do đã tồn tại //Bean
    // UserDetailService mà chưa có bean passwordencoder

}
