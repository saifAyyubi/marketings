package com.airbnb7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
public class SecurityConfig {

    private JWTRequestFilter jwtRequestFilter;

    public SecurityConfig(JWTRequestFilter jwtRequestFilter){
        this.jwtRequestFilter=jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable().cors().disable();

        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);

        http.authorizeHttpRequests().requestMatchers("/api/v1/users/addUser","/api/v1/users/login").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }
}
