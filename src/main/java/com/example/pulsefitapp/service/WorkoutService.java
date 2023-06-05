package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.WorkoutDto;
import com.example.pulsefitapp.model.Workout;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface WorkoutService {

    Workout save(Principal principal);
    void update(WorkoutDto workoutDto, Workout workout);
    List<Workout> loadWorkouts();
    void deleteWorkout(Long workoutId);
}