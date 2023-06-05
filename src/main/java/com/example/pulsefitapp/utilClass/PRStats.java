package com.example.pulsefitapp.utilClass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

// used in HomePage to calculate/display Personal Records and when Workout Saved to check if any current Workout
// Sets have become new Personal Records
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PRStats {

    private Float weight;
    private Integer repetitions;
    private LocalDate dateOfWorkout;
    private Long workoutId;

    public PRStats(Float weight, Integer repetitions, LocalDate dateOfWorkout){
        this.weight = weight;
        this.repetitions = repetitions;
        this.dateOfWorkout = dateOfWorkout;
    }

    public PRStats(Float weight, Integer repetitions, LocalDate dateOfWorkout, Long workoutId) {
        this.weight = weight;
        this.repetitions = repetitions;
        this.dateOfWorkout = dateOfWorkout;
        this.workoutId = workoutId;
    }
}
