package com.example.pulsefitapp.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExerciseDto {
    private String name;
    private String description;
    private String exerciseCategory;


    public ExerciseDto(String name, String description, String exerciseCategory) {
        this.name = name;
        this.description = description;
        this.exerciseCategory = exerciseCategory;
    }

}
