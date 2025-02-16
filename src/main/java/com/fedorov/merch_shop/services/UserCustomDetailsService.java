package com.fedorov.merch_shop.services;


import com.fedorov.merch_shop.models.UserCustom;
import com.fedorov.merch_shop.repositories.UserCustomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserCustomDetailsService implements UserDetailsService {

    @Autowired
    private UserCustomRepo userCustomRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserCustom userCustom = userCustomRepo.findByUsername(username);

        if (userCustom == null) {

            throw new UsernameNotFoundException("Пользователь не найден");

        }

        return User.builder()
                .username(userCustom.getUsername())
                .password(userCustom.getPassword())
                .roles(userCustom.getRole())
                .build();

    }

    public UserCustom createUser(String username, String password, String role) {

        UserCustom userCustom = UserCustom.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .account_balance(1000)
                .build();

        return userCustomRepo.save(userCustom);


    }
}
