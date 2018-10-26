package com.angel.inmemoryauthentication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class MyConfigurerAdapter extends WebSecurityConfigurerAdapter {

//    We must create password encoder by creating it as a bean which Spring
//    is going to add to Spring Application Context

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    We must extend WebSecurityConfigurerAdapter class and configure the users with the method below
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("user")).authorities("ROLE_USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("admin")).authorities("ROLE_ADMIN", "ROLE_USER");
    }

//    We must override the method below to configure which paths of our application are protected
//    and to restrict the access to some paths only for users with the granted authorities
//    The difference betweeb hasRole() and hasAuthority() is that hasRole() automatically adds 'ROLE_' in front of the role
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/confidential").hasRole("ADMIN")
                .antMatchers("/**").permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
