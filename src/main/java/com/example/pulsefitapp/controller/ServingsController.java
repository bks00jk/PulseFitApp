package com.example.pulsefitapp.controller;

import com.example.pulsefitapp.dto.*;
import com.example.pulsefitapp.model.*;
import com.example.pulsefitapp.repository.*;
import com.example.pulsefitapp.service.*;
import com.example.pulsefitapp.textUser.SmsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// Class creates/saves/deletes Servings that are saved/linked to the Meal User just created.
// Upon saving Meal (which is an update of empty Meal just created), checks to see if current Meal Calories takes User over
// Target Daily Calories amount, and if it does, sends a text message to User.
@Controller
@RequestMapping
public class ServingsController {

        private final ServingsService servingsService;
        private final FoodRepository foodRepository;
        private final MealService mealService;
        private final MealRepository mealRepository;
        private final UserService userService;
        private final HealthProfileRepository healthProfileRepository;
        private final SmsController smsController;

        List<Food> foodList;
        List<Servings> servingsList;
        Double totalDailyCals;

        @Autowired
        public ServingsController(ServingsService servingsService, FoodRepository foodRepository,
                                  MealService mealService, MealRepository mealRepository, UserService userService,
                                  HealthProfileRepository healthProfileRepository, SmsController smsController) {
            this.servingsService = servingsService;
            this.foodRepository = foodRepository;
            this.mealService = mealService;
            this.mealRepository = mealRepository;
            this.userService = userService;
            this.healthProfileRepository = healthProfileRepository;
            this.smsController = smsController;
        }

        @GetMapping("/servings")
        public String displayServings(Model model) {
            model.addAttribute("serving", new ServingsDto()); // binds "serving" attribute to the model
            model.addAttribute("meal", new MealDto()); // binds "meal" object
            // want to only display Sets that have just been created and linked to newly created Meal
            servingsList =  servingsService.loadServings();
            Meal currentMeal = mealRepository.findTopByOrderByIdDesc();


                List<Servings> currentMealServings = servingsList.stream()
                        .filter(s -> s.getMeal().getId() == currentMeal.getId()).collect(Collectors.toList());

                model.addAttribute("servings", currentMealServings);


            foodList = foodRepository.findAll();
            model.addAttribute("food", foodList);

            return "servings";
        }

        @PostMapping("/servings")
        public String saveServing(@ModelAttribute("servings") ServingsDto servingsDto){
            try {
                servingsService.save(servingsDto);
            } catch (Exception e){
                System.out.println("The following error occurred when attempting to save a Servings to the Database: " + e.getMessage());
            }

            return "redirect:/servings?success";
        }

        @PostMapping("/servings/delete/{servingId}")
        public String deleteServing(@PathVariable Long servingId) {
            try {
                servingsService.deleteServing(servingId);
            } catch (Exception e){
                System.out.println("The following error occurred when attempting to delete a Servings from the Database: " + e.getMessage());
            }

        return "redirect:/servings";
        }

        @PostMapping("/meal/update")
        public String saveMeal(@ModelAttribute("meal") MealDto mealDto, Principal principal){
            Meal currentMeal = mealRepository.findTopByOrderByIdDesc(); // retrieves the meal just created

            // Before Saving the New meal, Get ALl meals AND Target Calories from Today and see if current Meal Calories takes User over Target Calories amount. If yes, text user
            String email = principal.getName();
            User user = userService.loadUserByEmail(email);
            List<Meal> meals = mealRepository.findAllByUserId(user.getId());
            LocalDate mealDate = LocalDate.now();
            List<Meal> dailyMeals = meals.stream().filter(meal -> Objects.equals(meal.getDate(), mealDate)).toList();

            totalDailyCals = 0.0;

            dailyMeals.forEach(meal -> {
                totalDailyCals += meal.getMealCals();
            });

            List<HealthProfile> healthProfiles = healthProfileRepository.findAll().stream().filter(healthProfile1 -> healthProfile1.getUser().getId() == user.getId()).collect(Collectors.toList());

             Double targetCalories = healthProfiles.get(healthProfiles.size() - 1).getTargetCalories();

             // Target calories - Total Calories from meals eaten today - Current Meal Calories
            Double overUnderCals = targetCalories - totalDailyCals - mealDto.getMealCals();
            System.out.println("Target Calories" + targetCalories);
            System.out.println("Total Daily Calories So Far" + totalDailyCals);
            System.out.println("Current Meal Calories" + mealDto.getMealCals());
            System.out.println("If + you have this remaining. If - you are over by this amount: " + overUnderCals);

            // if current meal takes user over target calories amount for today, send a text
            if(overUnderCals < 0){
                String message = "Hey, " + user.getFirstName() +"! This message is to inform you that you have gone over you Target Calories amount for today by " + overUnderCals * -1 + " calories. Changing your eating habits is tough, but a healthy life it worth it!!!";
                String phoneNumber = user.getPhone();
                SmsRequestDto messageUser = new SmsRequestDto(phoneNumber, message);

                try {
                    smsController.sendSms(messageUser);
                } catch (Exception e){
                    System.out.println("The following error occurred when attempting to send a text message to the User: " + e.getMessage());
                }

            }

            try {
                mealService.update(mealDto, currentMeal);
            } catch (Exception e){
                System.out.println("The following error occurred when attempting to update a Meal in the Database: " + e.getMessage());
            }

            return "redirect:/meals";
        }
    }

