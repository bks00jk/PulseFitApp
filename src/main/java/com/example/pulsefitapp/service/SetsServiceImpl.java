package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.SetsDto;
import com.example.pulsefitapp.model.*;
import com.example.pulsefitapp.repository.ExerciseRepository;
import com.example.pulsefitapp.repository.SetsRepository;
import com.example.pulsefitapp.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

// Saves, loads, and deletes Sets
@Service
public class SetsServiceImpl implements SetsService {

    private final SetsRepository setsRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final UserService userService;

    @Autowired
    public SetsServiceImpl(SetsRepository setsRepository, ExerciseRepository exerciseRepository,
                           WorkoutRepository workoutRepository, UserService userService) {
        this.setsRepository = setsRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
        this.userService = userService;
    }

    @Override
    public Sets save(SetsDto setsDto) {
        Exercise exercise = exerciseRepository.findByName(setsDto.getExercise());
        // Need to use Workout id from user workout just created/ retrieve that Workout object instead of creating new one
        Workout workout = workoutRepository.findTopByOrderByIdDesc();
        Sets sets = new Sets(setsDto.getRepetitions(), setsDto.getWeight(), exercise, workout);

        return setsRepository.save(sets);
    }

    @Override
    public List<Sets> loadSets() {
        return setsRepository.findAll();
    }

    @Override
    public List<Sets> loadSetsByUserId(Principal principal) {
        String email = principal.getName();
        User user = userService.loadUserByEmail(email);
        return setsRepository.findAllByUserId(user.getId());
    }

    @Override
    public void deleteSet(Long id) {
        setsRepository.deleteById(id);
    }
}

