package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.FoodCategoryDto;
import com.example.pulsefitapp.model.FoodCategory;

import java.util.List;

public interface FoodCategoryService {
    FoodCategory save(FoodCategoryDto categoryDto);
    List<FoodCategory> loadFoodCategories();

}
