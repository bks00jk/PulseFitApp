package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.MealDto;
import com.example.pulsefitapp.model.Meal;
import com.example.pulsefitapp.model.User;
import com.example.pulsefitapp.repository.MealRepository;
import com.example.pulsefitapp.repository.ServingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

// Saves empty Meal object upon User clicking "Create Meal" button on Homepage
// Updates Meal w/user inputted data after User Creates/Adds Servings and "Saves Meal"
// Loads all Meals for display and allows User to delete a Meal
@Service
public class MealServiceImpl implements MealService {


    private final MealRepository mealRepository;
    private final UserService userService;
    private final ServingsRepository servingsRepository;

    @Autowired
    public MealServiceImpl(MealRepository mealRepository, UserService userService, ServingsRepository servingsRepository) {
        this.mealRepository = mealRepository;
        this.userService = userService;
        this.servingsRepository = servingsRepository;
    }

    // to create Meal before adding Servings, other data; just user object and auto created id
    @Override
    public Meal save(Principal principal) {
        String email = principal.getName();
        User user = userService.loadUserByEmail(email);
        Meal meal = new Meal(user);
        return mealRepository.save(meal);
    }



    // saves Meal after adding name, date, time info AFTER creating/adding all Servings
    @Override
    public void update(MealDto mealDto, Meal meal) {
        int formattedHour;
        int hour = mealDto.getHour();
        String dayNight = mealDto.getDayNight();

        if((dayNight.toLowerCase().startsWith("a") && hour != 12) || dayNight.toLowerCase().startsWith("p") && hour == 12 ) {
            formattedHour = hour;
        } else if(dayNight.toLowerCase().startsWith("p") && hour != 12) {
            formattedHour = hour + 12;
        } else {
            formattedHour = 0;
        }

        LocalDate date = LocalDate.of(mealDto.getYear(), mealDto.getMonth(), mealDto.getDay());
        LocalTime time = LocalTime.of(formattedHour, mealDto.getMinute(), 0);

        mealRepository.updateMealById(date, time, mealDto.getMealType(),
                mealDto.getMealCals(), mealDto.getMealFat(), mealDto.getMealCarbs(), mealDto.getMealProtein(), meal.getId());
    }



    @Override
    public List<Meal> loadMeals() {
        return mealRepository.findAll();
    }

    @Override
    public void deleteMeal(Long mealId) {
        Meal meal = mealRepository.findMealById(mealId);
        meal.getServings().forEach(servings -> servingsRepository.deleteById(servings.getId()));
        mealRepository.deleteMealById(mealId);
    }


}

