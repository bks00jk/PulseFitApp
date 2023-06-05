package com.example.pulsefitapp.controller;

import com.example.pulsefitapp.model.*;
import com.example.pulsefitapp.repository.HealthProfileRepository;
import com.example.pulsefitapp.repository.MealRepository;
import com.example.pulsefitapp.repository.WorkoutRepository;
import com.example.pulsefitapp.service.UserService;
import com.example.pulsefitapp.utilClass.SelectedDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

// This class: (1) displays current Health Profile and User Info inputted at registration for possible updates on page load
// (2) When User selects a specific date, displays Meal Cards/Health Data/Workout Cards pertaining to that date.
@Controller
public class ProfileController {

  private final UserService userService;
  private final MealRepository mealRepository;
  private final WorkoutRepository workoutRepository;
  private final HealthProfileRepository healthProfileRepository;

  List<Meal> dailyMeals;
  List<Workout> dailyWorkouts;
  Double dailyCals = 0.0;
  Double modalDailyCals = 0.0;
  Double modalDailyProtein = 0.0;
  Double modalDailyCarbs = 0.0;
  Double modalDailyFat = 0.0;


  LocalDate mealDate;
  LocalDate workoutDate;
  List<HealthProfile> healthProfiles;
  HealthProfile currentHealthProfile;
  HealthProfile dailyHealthProfile;
  Double dailyTargetCals = 0.0;
  Double dailyWeight = 0.0;


    @Autowired
    public ProfileController(UserService userService, MealRepository mealRepository, WorkoutRepository workoutRepository,
                             HealthProfileRepository healthProfileRepository){
        this.userService = userService;
        this.mealRepository = mealRepository;
        this.workoutRepository = workoutRepository;
        this.healthProfileRepository = healthProfileRepository;

    }

    //On page load displays a User's current Daily Health Profile data, all User's data inputted in registration form that can be updated here,
    //And binds various attributes to the model to be used to display a User's daily Meal Cards, Meal/Health Data, and Workout Cards when date selected.
    @GetMapping("/profile")
    public String displayUserProfile(Principal principal, Model model) {
        String email = principal.getName();
        User user = userService.loadUserByEmail(email);
        model.addAttribute("user", user);

        List<Meal> meals = mealRepository.findAllByUserId(user.getId());

        Set<LocalDate> mealDates = meals.stream().map(Meal::getDate).collect(Collectors.toSet());


        model.addAttribute("selectedDate", new SelectedDate());
        model.addAttribute("mealDates", mealDates);
        model.addAttribute("dailyCals", dailyCals);

        List<Workout> workouts = workoutRepository.findAllByUserId(user.getId());
        Set<LocalDate> workoutDates = workouts.stream().map(Workout::getDateOfWorkout).collect(Collectors.toSet());

        model.addAttribute("workoutDates", workoutDates);
        model.addAttribute("dailyMeals", dailyMeals);
        model.addAttribute("dailyWorkouts", dailyWorkouts);

        model.addAttribute("modalDailyCals", modalDailyCals);
        model.addAttribute("modalDailyProtein", Math.round(modalDailyProtein));
        model.addAttribute("modalDailyCarbs", Math.round(modalDailyCarbs));
        model.addAttribute("modalDailyFat", Math.round(modalDailyFat));

        model.addAttribute("mealDate", mealDate);
        model.addAttribute("workoutDate", workoutDate);

        // Find all user's healthProfiles, get most recent, add to model to display
        healthProfiles = healthProfileRepository.findAll().stream().filter(healthProfile1 -> healthProfile1.getUser().getId() == user.getId()).collect(Collectors.toList());

        if(healthProfiles.size() > 0){
            currentHealthProfile = healthProfiles.get(healthProfiles.size() - 1);

        } else {
            currentHealthProfile = new HealthProfile(LocalDate.now(), 0.00F, (double) 0, (double) 0, "", user);
        }
        model.addAttribute("healthProfile", currentHealthProfile);

        model.addAttribute("targetCals", currentHealthProfile.getTargetCalories());
        model.addAttribute("dailyTargetCals", dailyTargetCals);
        model.addAttribute("dailyWeight", dailyWeight);
        System.out.println("Inside GET /profile");
        System.out.println(currentHealthProfile);

        return "profile";
    }

// After User selects a specific date, this method contains logic to display all of a User's Meals Cards, Health Data, and
// Workout Information for the specified date.
    @PostMapping("/profile/meals")
    public String displayMealsSummary(@ModelAttribute("selectedDate") SelectedDate selectedDate, Principal principal, Model model) {
        String email = principal.getName();
        User user = userService.loadUserByEmail(email);
        List<Meal> meals = mealRepository.findAllByUserId(user.getId());
        mealDate = LocalDate.parse(selectedDate.getDate());
        dailyMeals = meals.stream().filter(meal -> Objects.equals(meal.getDate(), mealDate)).toList();

        modalDailyCals = 0.0;
        modalDailyProtein = 0.0;
        modalDailyCarbs = 0.0;
        modalDailyFat = 0.0;

        dailyMeals.forEach(meal -> {
            modalDailyCals += meal.getMealCals();
            modalDailyProtein += meal.getMealProtein();
            modalDailyCarbs += meal.getMealCarbs();
            modalDailyFat += meal.getMealFat();
        });


        List<Workout> workouts = workoutRepository.findAllByUserId(user.getId());

        dailyWorkouts = workouts.stream().filter(workout -> Objects.equals(workout.getDateOfWorkout(), mealDate)).toList();

        List<HealthProfile> dailyHealthProfiles = healthProfileRepository.findDailyByUserId(user.getId(), mealDate);

       System.out.println(mealDate);
       System.out.println(dailyHealthProfiles);

       //sets currentHealthProfile to the last of the list of hps created for a date (User may create many HPs each day to test calculations but most recent is one they intend to use)
        if(dailyHealthProfiles.size() > 0){
            dailyHealthProfile = dailyHealthProfiles.get(dailyHealthProfiles.size() - 1);
        } else {
            dailyHealthProfile = currentHealthProfile;
        }

        if(dailyHealthProfile.getTargetCalories() == null){
            dailyTargetCals = 0.0;
        } else {
            dailyTargetCals = dailyHealthProfile.getTargetCalories();
        }

        dailyWeight = (double) Math.round(dailyHealthProfile.getWeight());
        System.out.println("Your daily weight is" + dailyHealthProfile.getWeight());
        model.addAttribute("dailyTargetCals", dailyTargetCals);
        model.addAttribute("dailyWeight", dailyWeight);

        System.out.println("hello from Profile Controller GET /profile/meals/");

        System.out.println("dailyHealthProfile" + dailyHealthProfile);
        System.out.println("dailyTargetCals" + dailyHealthProfile.getTargetCalories());
        System.out.println("modalDailyCals" + modalDailyCals);

        return "redirect:/profile";
    }

}
