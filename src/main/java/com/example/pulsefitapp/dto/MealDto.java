package com.example.pulsefitapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MealDto {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String dayNight;


    private String mealType;
    private Float mealCals;
    private Float mealFat;
    private Float mealCarbs;
    private Float mealProtein;

    public MealDto(int year, int month, int day, int hour, int minute, String dayNight, String mealType, Float mealFat, Float mealCarbs, Float mealProtein, Float mealCals) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.dayNight = dayNight;
        this.mealType = mealType;
        this.mealCals = mealCals;
        this.mealFat = mealFat;
        this.mealCarbs = mealCarbs;
        this.mealProtein = mealProtein;
    }
}