package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.UserRegistrationDto;
import com.example.pulsefitapp.exception.UserAlreadyExistsException;
import com.example.pulsefitapp.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;

// UserDetailsService is part of Spring Security used to protect User credentials
public interface UserService extends UserDetailsService {

    User save(UserRegistrationDto registrationDto) throws UserAlreadyExistsException;
    User loadUserByEmail(String email);

    void update(UserRegistrationDto userRegistrationDto, Principal principal);
    void delete(Principal principal);
}
