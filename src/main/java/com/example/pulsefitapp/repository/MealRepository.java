package com.example.pulsefitapp.repository;

import com.example.pulsefitapp.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    // finds most recently created Meal in DB overall
    Meal findTopByOrderByIdDesc();

    // used when Saving a Meal (updates empty Meal just created) on Servings Page after Sets created/linked to Meal
    // Nutritional Data auto calculated and User enters additional Meal information
    @Modifying
    @Transactional
    @Query("update Meal m set m.date = ?1, m.time = ?2, m.mealType = ?3, m.mealCals = ?4, m.mealFat = ?5, m.mealCarbs = ?6, m.mealProtein = ?7 where m.id = ?8")
    void updateMealById(LocalDate date, LocalTime time, String mealType, Float mealCals, Float mealFat, Float mealCarbs, Float mealProtein, Long id);


    @Query("from Meal m where m.user.id = ?1")
    List<Meal> findAllByUserId(Long id);

    @Query("from Meal m where m.id = ?1")
    Meal findMealById(Long id);

    @Modifying
    @Transactional
    @Query("delete Meal m where m.id = ?1")
    void deleteMealById(Long id);


}
