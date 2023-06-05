package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.ExerciseDto;
import com.example.pulsefitapp.model.Exercise;
import com.example.pulsefitapp.model.ExerciseCategory;
import com.example.pulsefitapp.model.Sets;
import com.example.pulsefitapp.repository.ExerciseCategoryRepository;
import com.example.pulsefitapp.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


// Saves/loads Exercises
@Service
public class ExerciseServiceImpl implements ExerciseService {


    private final ExerciseRepository exerciseRepository;
    private final ExerciseCategoryRepository exerciseCategoryRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, ExerciseCategoryRepository exerciseCategoryRepository) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseCategoryRepository = exerciseCategoryRepository;
    }

     // Saves Exercise by first retrieving ExerciseCategory based on String name inputted in ExerciseDto by user,
    // then it appends Category name as prefix to Exercise name to be saved in that form so that easier for User to filter
    // when creating Sets for Workouts
    @Override
    public Exercise save(ExerciseDto exerciseDto) {
          ExerciseCategory category = exerciseCategoryRepository.findByName(exerciseDto.getExerciseCategory());
          String exercisePlusCatName = exerciseDto.getExerciseCategory() + "-" + exerciseDto.getName();
          Exercise exercise = new Exercise(exercisePlusCatName, exerciseDto.getDescription(), category, new HashSet<Sets>());

        return exerciseRepository.save(exercise);
    }


    @Override
    public List<Exercise> loadExercises() {
        return exerciseRepository.findAll();
    }
}
