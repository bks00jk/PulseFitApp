package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.FoodDto;
import com.example.pulsefitapp.model.Food;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class FoodServiceTests {


    @Autowired
    private FoodServiceImpl foodService;

    @Test
    public void testLoadExercises(){
        FoodDto foodDto = new FoodDto();
        Food food = foodService.save(foodDto); //saves a new Food to DB

        List<Food> foodList = foodService.loadFood(); //loads all Food

        Assertions.assertThat(foodList.size()).isNotZero(); // we know there should be at least 1 Food in DB
    }

}

