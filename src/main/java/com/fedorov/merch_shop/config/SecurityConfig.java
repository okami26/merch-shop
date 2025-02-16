package com.fedorov.merch_shop.config;

import com.fedorov.merch_shop.services.UserCustomDetailsService;
import com.fedorov.merch_shop.utils.JwtFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilterChain jwtFilterChain;

    @Autowired
    private UserCustomDetailsService userCustomDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtFilterChain, UsernamePasswordAuthenticationFilter.class)
                .csrf(c -> c.disable())

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth").permitAll()
                        .anyRequest().authenticated()

                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserCustomDetailsService userCustomDetailsService,
                                                       PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userCustomDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);


        return new ProviderManager(authenticationProvider);

    }








}