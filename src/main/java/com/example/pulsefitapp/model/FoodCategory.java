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
@Table(name = "foodCategory")
public class FoodCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;


    @JsonIgnore
    @OneToMany(mappedBy = "foodCategory")
    private Set<Food> food = new HashSet<>();


    public FoodCategory(String name, String description, Set<Food> food) {
        this.name = name;
        this.description = description;
        this.food = food;
    }

}

