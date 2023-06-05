package com.example.pulsefitapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FoodDto {
    private String name;
    private Float servingSize;
    private Integer calories;
    private Float fat;
    private Float carbs;
    private Float protein;
    private String foodCategory;


    public FoodDto(String name, Float servingSize, Integer calories, Float fat, Float carbs,
                   Float protein, String foodCategory) {
        this.name = name;
        this.servingSize = servingSize;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
        this.foodCategory = foodCategory;
    }

}