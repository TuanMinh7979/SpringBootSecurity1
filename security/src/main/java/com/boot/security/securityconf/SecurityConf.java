package com.boot.security.securityconf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.boot.security.securityconf.AppRole.ADMIN;
import static com.boot.security.securityconf.AppRole.ADMINTRAINEE;
import static com.boot.security.securityconf.AppRole.STUDENT;

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
        // thuc se lam ten bean va chi co 1 bean mang type la class do
        // ) thi co the
        // inject no
        // thong qua constructor
        @Autowired
        public SecurityConf(PasswordEncoder passwordEncoder) {
                this.passwordEncoder = passwordEncoder;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                // neu khong su dung request trong antMatchers thì mặc định tất cả request đều
                // nhận.
                // antmatcher có XÉT THỨ TỰ, vậy nên những cái chung hay gốc rễ nên đặt ở dưới.

                http
                                .csrf().disable()
                                .authorizeRequests()
                                .antMatchers("/", "/index", "/css/*", "/js/*")
                                .permitAll()

                                .antMatchers("/student/**").hasRole(AppRole.STUDENT.name())
                                .antMatchers(HttpMethod.DELETE, "/api/**")
                                .hasAuthority(AppUserPermission.COURSE_WRITE.getPermission())
                                .antMatchers(HttpMethod.POST, "/api/**")
                                .hasAuthority(AppUserPermission.COURSE_WRITE.getPermission())
                                .antMatchers(HttpMethod.PUT, "/api/**")
                                .hasAuthority(AppUserPermission.COURSE_WRITE.getPermission())
                                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
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
                UserDetails tuanuser = User.builder()
                                .username("tuanuser")
                                .password(passwordEncoder.encode("123"))
                                // .roles(STUDENT.name())
                                .authorities(STUDENT.getAuthorities())
                                .build();
                UserDetails tuanadmin = User.builder()
                                .username("tuanadmin")
                                .password(passwordEncoder.encode("123"))
                                // .roles(ADMIN.name())
                                .authorities(ADMIN.getAuthorities())
                                .build();
                UserDetails tuanread = User.builder()
                                .username("tuanread")
                                .password(passwordEncoder.encode("123"))
                                // .roles(ADMINTRAINEE.name())
                                .authorities(ADMINTRAINEE.getAuthorities())
                                .build();

                return new InMemoryUserDetailsManager(
                                tuanuser, tuanadmin, tuanread);
        }
        // Bug here: sẽ bị lỗi no password encoder do đã tồn tại //Bean
        // UserDetailService mà chưa có bean passwordencoder

}
