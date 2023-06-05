package com.example.pulsefitapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class WorkoutDto {
    private String workoutName;
    private Float duration;
    private int year;
    private int month;
    private int day;

    public WorkoutDto(int month, int day, int year, String workoutName, Float duration){

        this.month = month;
        this.day = day;
        this.year = year;
        this.workoutName = workoutName;
        this.duration = duration;
    }
}
