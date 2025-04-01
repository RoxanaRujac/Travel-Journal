package com.travel.authentication.service.impl;

import com.travel.authentication.dto.AuthDTO;
import com.travel.authentication.model.User;
import com.travel.authentication.repository.UserRepository;
import com.travel.authentication.service.AuthService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(AuthDTO auth) {
        User user = userRepository.findByUsernameAndPassword(auth.getUsername(), auth.getPassword());
        if (user == null) {
            throw new NoSuchElementException("User not found");
        }
        return user;
    }
}
