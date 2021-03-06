package com.homework22.config;

import com.homework22.enums.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/used").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin();
        }

        @Bean
        @Override
        protected UserDetailsService userDetailsService() {
            return new InMemoryUserDetailsManager(
                    User.builder()
                            .username("user")
                            .password(passwordEncoder().encode("user"))
                            .roles(Roles.USER.name())
                            .build(),

                    User.builder()
                            .username("admin")
                            .password(passwordEncoder().encode("admin"))
                            .roles(Roles.ADMIN.name())
                            .build()
            );
        }

        @Bean
        protected PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder(12);
        }
    }