package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.FoodDto;
import com.example.pulsefitapp.model.Food;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FoodService {
    Food save(FoodDto foodDto);
    List<Food> loadFood();
}