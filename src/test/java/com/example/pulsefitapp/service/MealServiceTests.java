package com.example.pulsefitapp.service;

import com.example.pulsefitapp.model.Meal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.security.Principal;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class MealServiceTests {


 @Autowired
 private MealServiceImpl mealService;

 @Test
 public void testSaveMeal() {

   Principal principal = new Principal() {
   @Override
   public String getName() {
    return "Jim";
   }
  };


  Meal savedMeal = mealService.save(principal);
  Assertions.assertThat(savedMeal.getId()).isNotNull();
 }

 }


