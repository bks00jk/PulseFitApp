package com.example.pulsefitapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "servings")
@Getter
@Setter
@NoArgsConstructor
public class Servings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private Float number;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="meal_id", referencedColumnName = "id")
    private Meal meal;


    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name="food_id", referencedColumnName = "id")
    private Food food;


    public Servings(Float number, Meal meal, Food food){
       this.number = number;
       this.meal = meal;
       this.food = food;
    }
}

