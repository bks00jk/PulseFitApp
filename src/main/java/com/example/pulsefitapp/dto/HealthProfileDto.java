package com.example.pulsefitapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class HealthProfileDto {

    private Float weight;
    private Double exerciseLevel;
    private Double targetCalories;
    private String weightGoal;

    public HealthProfileDto(Float weight, Double exerciseLevel, Double targetCalories, String weightGoal) {
        this.weight = weight;
        this.exerciseLevel = exerciseLevel;
        this.targetCalories = targetCalories;
        this.weightGoal = weightGoal;
    }

}
