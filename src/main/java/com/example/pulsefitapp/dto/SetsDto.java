package com.example.pulsefitapp.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SetsDto {
    private Integer repetitions;
    private Float weight;
    private String exercise;

    public SetsDto(Integer repetitions, Float weight, String exercise){
        this.repetitions = repetitions;
        this.weight = weight;
        this.exercise = exercise;
    }
}
