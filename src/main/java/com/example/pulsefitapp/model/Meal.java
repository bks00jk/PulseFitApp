package com.example.pulsefitapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "meal")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String mealType;
    private Float mealCals;
    private Float mealFat;
    private Float mealCarbs;
    private Float mealProtein;

    @JsonIgnore
    @OneToMany(mappedBy = "meal", fetch = FetchType.EAGER)
    private Set<Servings> servings = new HashSet<>();

    public Meal(LocalDate date, LocalTime time, String mealType, Float mealCals, Float mealFat, Float mealCarbs, Float mealProtein, User user, Set<Servings> servings) {
        this.date = date;
        this.time = time;
        this.user = user;
        this.mealType = mealType;
        this.mealCals = mealCals;
        this.mealFat = mealFat;
        this.mealCarbs = mealCarbs;
        this.mealProtein = mealProtein;
        this.servings = servings;
    }

    public Meal(User user){
        this.user = user;
    }
}
