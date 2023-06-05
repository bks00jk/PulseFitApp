package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.ExerciseCategoryDto;
import com.example.pulsefitapp.model.ExerciseCategory;

import java.util.List;


public interface ExerciseCategoryService {
    ExerciseCategory save(ExerciseCategoryDto categoryDto);
    List<ExerciseCategory> loadExerciseCategories();

}
