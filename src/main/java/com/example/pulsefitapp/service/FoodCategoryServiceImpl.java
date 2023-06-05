package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.FoodCategoryDto;
import com.example.pulsefitapp.model.Food;
import com.example.pulsefitapp.model.FoodCategory;
import com.example.pulsefitapp.repository.FoodCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

// Saves/Loads Food Categories
@Service
public class FoodCategoryServiceImpl implements FoodCategoryService{

    private final FoodCategoryRepository categoryRepository;

    @Autowired
    public FoodCategoryServiceImpl(FoodCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public FoodCategory save(FoodCategoryDto categoryDto) {
        FoodCategory foodCategory = new FoodCategory(categoryDto.getName(), categoryDto.getDescription(), new HashSet<Food>());
        return categoryRepository.save(foodCategory);
    }

    @Override
    public List<FoodCategory> loadFoodCategories() {
        return categoryRepository.findAll();
    }
}
