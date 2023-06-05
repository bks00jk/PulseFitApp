package com.example.pulsefitapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "food")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Float servingSize;
    private Integer calories;
    private Float fat;
    private Float carbs;
    private Float protein;

    @ManyToOne
    @JoinColumn(name="foodCategory_id", referencedColumnName = "id")
    private FoodCategory foodCategory;


    @JsonIgnore
    @OneToMany(mappedBy = "food")
    private Set<Servings> servings = new HashSet<>();

    public Food(String name, Float servingSize, Integer calories, Float fat, Float carbs, Float protein, FoodCategory foodCategory,
                Set<Servings> servings) {
        this.name = name;
        this.servingSize = servingSize;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
        this.foodCategory = foodCategory;
        this.servings = servings;
    }
}
