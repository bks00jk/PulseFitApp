package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.MealDto;
import com.example.pulsefitapp.model.Meal;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface MealService {

    Meal save(Principal principal);
    void update(MealDto mealDto, Meal meal);
    List<Meal> loadMeals();
    void deleteMeal(Long mealId);
}
