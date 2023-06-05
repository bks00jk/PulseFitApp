package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.ExerciseDto;
import com.example.pulsefitapp.model.Exercise;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExerciseService {
    Exercise save(ExerciseDto exerciseDto);
    List<Exercise> loadExercises();
}
