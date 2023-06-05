package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.WorkoutDto;
import com.example.pulsefitapp.model.*;
import com.example.pulsefitapp.repository.SetsRepository;
import com.example.pulsefitapp.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

// Creates, Updates, Deletes, and Loads Workouts
@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final SetsRepository setsRepository;
    private final UserService userService;

    @Autowired
    public WorkoutServiceImpl(WorkoutRepository workoutRepository, UserService userService, SetsRepository setsRepository) {
        this.setsRepository = setsRepository;
        this.workoutRepository = workoutRepository;
        this.userService = userService;
    }

    // to create workout before adding sets, other data; just user object and auto created id
    @Override
    public Workout save(Principal principal) {
        String email = principal.getName();
        User user = userService.loadUserByEmail(email);
        Workout workout = new Workout(user);
        return workoutRepository.save(workout);
    }


    // save workout after adding name,duration, date info AFTER creating/adding all Sets
    @Override
    public void update(WorkoutDto workoutDto, Workout workout) {
        LocalDate date = LocalDate.of(workoutDto.getYear(), workoutDto.getMonth(), workoutDto.getDay());
        workoutRepository.updateWorkoutById(date, workoutDto.getDuration(), workoutDto.getWorkoutName(), workout.getId());
    }

    @Override
    public List<Workout> loadWorkouts() {
        return workoutRepository.findAll();
    }

    @Override
    public void deleteWorkout(Long workoutId) {
        Workout workout = workoutRepository.findWorkoutById(workoutId);
        workout.getSets().forEach(sets -> setsRepository.deleteById(sets.getId()));
        workoutRepository.deleteWorkoutById(workoutId);
    }

}

