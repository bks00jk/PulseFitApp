package com.example.pulsefitapp.repository;

import com.example.pulsefitapp.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class MealRepositoryTests {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testUpdateMeal() {
        //creates a new "empty" Meal object w/2 fields: id, user_id and saves to DB
        Meal meal = new Meal(new User());
        Meal savedMeal = mealRepository.save(meal);

        //retrieves savedMeal from DB by id
        Meal retrievedMeal = mealRepository.findMealById(savedMeal.getId());

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        //calls updateMealById and injects remainder of Meal model data into constructor w/meal id of retrieved existMeal
        mealRepository.updateMealById(today, now, "breakfast", 200F, 10F, 8F, 20F, retrievedMeal.getId());


        //tests that the savedMeal and existMeal that was updated and retrieved has the same date
        Assertions.assertThat(retrievedMeal.getDate()).isEqualTo(today);

    }


    @Test
    public void testRetrieveMostRecentMealInDb() {
        //creates a new "empty" Meal object w/2 fields: id, user_id and saves to DB
        Meal meal = new Meal(new User());
        Meal savedMeal = mealRepository.save(meal);

        // retrieves most recent created Meal in DB
        Meal mostRecent = mealRepository.findTopByOrderByIdDesc();


        Assertions.assertThat(savedMeal.getId()).isEqualTo(mostRecent.getId());

    }

    @Test
    public void testFindAllMealsByUserId() {

        User user = new User();
        User savedUser = userRepository.save(user);
        Meal meal1 = new Meal(user);
        Meal savedMeal1 = mealRepository.save(meal1);
        Meal meal2 = new Meal(user);
        Meal savedMeal2 = mealRepository.save(meal2);

        List<Meal> mealList = mealRepository.findAllByUserId(savedUser.getId());

        Assertions.assertThat(mealList.size()).isEqualTo(2);

    }

    @Test
    public void testDeleteMealById() {
        Meal meal = new Meal();
        Meal savedMeal = mealRepository.save(meal);

        mealRepository.deleteMealById(savedMeal.getId());

        Meal findDeletedMeal = mealRepository.findMealById(savedMeal.getId());

        Assertions.assertThat(findDeletedMeal).isNull();


    }

}