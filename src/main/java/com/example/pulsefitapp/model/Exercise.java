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
@Table(name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "description", length = 50)
    private String description;


    @ManyToOne
    @JoinColumn(name="exerciseCategory_id", referencedColumnName = "id")
    private ExerciseCategory exerciseCategory;

    @JsonIgnore
    @OneToMany(mappedBy = "exercise")
    private Set<Sets> sets = new HashSet<>();


    public Exercise(String name, String description, ExerciseCategory exerciseCategory, Set<Sets> sets) {
        this.name = name;
        this.description = description;
        this.exerciseCategory = exerciseCategory;
        this.sets = sets;
    }

}