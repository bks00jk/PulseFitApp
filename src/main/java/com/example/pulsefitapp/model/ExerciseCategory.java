package com.example.pulsefitapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "exerciseCategory")
public class ExerciseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "exerciseCategory")
    private Set<Exercise> exercises = new HashSet<>();

    public ExerciseCategory(String name, String description, Set<Exercise> exercises) {
        this.name = name;
        this.description = description;
        this.exercises = exercises;
    }

}
