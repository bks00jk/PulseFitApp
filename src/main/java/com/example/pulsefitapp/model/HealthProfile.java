package com.example.pulsefitapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "healthProfile")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HealthProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "exercise_level")
    private Double exerciseLevel;

    @Column(name = "target_calories")
    private Double targetCalories;

    @Column(name = "weight_goal")
    private String weightGoal;

   // changed to mirror Workout and Meal ManyToOne properties.
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    public HealthProfile(LocalDate date, Float weight, Double exerciseLevel, Double targetCalories, String weightGoal, User user){
        this.date = date;
        this.weight = weight;
        this.exerciseLevel = exerciseLevel;
        this.targetCalories = targetCalories;
        this.weightGoal = weightGoal;
        this.user = user;
    }
}

