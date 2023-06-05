package com.example.pulsefitapp.dto;


import com.example.pulsefitapp.model.ExerciseCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ExerciseCategoryDto {

    private String name;
    private String description;
    private ExerciseCategory exerciseCategory;

    public ExerciseCategoryDto(String name, String description, ExerciseCategory exerciseCategory) {
        this.name = name;
        this.description = description;
        this.exerciseCategory = exerciseCategory;
    }

}
