package com.example.pulsefitapp.service;

import com.example.pulsefitapp.model.Workout;
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
public class WorkoutServiceTests {


    @Autowired
    private WorkoutServiceImpl workoutService;

    @Test
    public void testSaveWorkout() {

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "Bill";
            }
        };


        Workout savedWorkout = workoutService.save(principal);
        Assertions.assertThat(savedWorkout.getId()).isNotNull();
    }

}


