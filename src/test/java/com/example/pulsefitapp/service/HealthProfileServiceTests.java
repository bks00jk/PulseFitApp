package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.HealthProfileDto;
import com.example.pulsefitapp.model.HealthProfile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.security.Principal;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class HealthProfileServiceTests {


    @Autowired
    private HealthProfileServiceImpl healthProfileService;

    @Test
    public void testSaveHealthProfile() {
        HealthProfileDto healthProfileDto = new HealthProfileDto();
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "John";
            }
        };


        HealthProfile savedHealthProfile = healthProfileService.save(healthProfileDto, principal);
        Assertions.assertThat(savedHealthProfile.getId()).isNotNull();
    }

}