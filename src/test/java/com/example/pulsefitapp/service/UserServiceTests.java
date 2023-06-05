package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.UserRegistrationDto;
import com.example.pulsefitapp.exception.UserAlreadyExistsException;
import com.example.pulsefitapp.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserServiceTests {


    @Autowired
    private UserServiceImpl userService;

    @Test
    public void testSaveUser() throws UserAlreadyExistsException {

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setEmail("testemail@test.com");
        userRegistrationDto.setPassword("12345");
        User savedUser = userService.save(userRegistrationDto);
        Assertions.assertThat(savedUser.getId()).isNotNull();
    }

}


