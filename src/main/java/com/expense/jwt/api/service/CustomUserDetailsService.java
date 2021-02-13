package com.expense.jwt.api.service;


import com.expense.jwt.api.beans.KasherdhamUser;
import com.expense.jwt.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("----email loading of ---->{}",email);
        KasherdhamUser fetchedUser = userRepository.findByEmail(email);
        log.info("username ===>{}",fetchedUser.getUsername());
        log.info("username ===>{}",fetchedUser.getEmail());
        return new org.springframework.security.core.userdetails.User(fetchedUser.getEmail(), fetchedUser.getPassword(), new ArrayList<>());
    }
}
