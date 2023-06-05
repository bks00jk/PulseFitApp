package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.ExerciseCategoryDto;
import com.example.pulsefitapp.model.Exercise;
import com.example.pulsefitapp.model.ExerciseCategory;
import com.example.pulsefitapp.repository.ExerciseCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

// Saves/Loads Exercise Categories
@Service
public class ExerciseCategoryServiceImpl implements ExerciseCategoryService{

    private final ExerciseCategoryRepository categoryRepository;

    @Autowired
    public ExerciseCategoryServiceImpl(ExerciseCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public ExerciseCategory save(ExerciseCategoryDto categoryDto) {
        ExerciseCategory exerciseCategory = new ExerciseCategory(categoryDto.getName(), categoryDto.getDescription(), new HashSet<Exercise>());
        return categoryRepository.save(exerciseCategory);
    }

    @Override
    public List<ExerciseCategory> loadExerciseCategories() {
        return categoryRepository.findAll();
    }
}