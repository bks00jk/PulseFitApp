package com.example.pulsefitapp.controller;


import com.example.pulsefitapp.dto.HealthProfileDto;
import com.example.pulsefitapp.model.HealthProfile;
import com.example.pulsefitapp.model.Sets;
import com.example.pulsefitapp.model.User;
import com.example.pulsefitapp.service.*;
import com.example.pulsefitapp.utilClass.PRStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Class contains routes/logic to display "login" page, "homepage" ("/" route), and to create/save a Daily Health Profile
@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    SetsService setsService;
    UserService userService;
    HealthProfileService healthProfileService;

    @Autowired
    MainController(SetsService setsService, UserService userService, HealthProfileService healthProfileService) {
        this.setsService = setsService;
        this.userService = userService;
        this.healthProfileService = healthProfileService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //Bind attributes to Daily Health Profile Form and to create User's Personal Exercise Record Table on page load
    @GetMapping("/")
    public String home(Model model, Principal principal) {
        List<Sets> allSets = setsService.loadSetsByUserId(principal);

        // ExerciseStats is util class containing 3 fields (weight / repetitions / dateOfWorkout)
        Map<String, PRStats> personalRecords = new HashMap<>();

        allSets.forEach(sets -> {
            String exerciseName = sets.getExercise().getName();
            Float weight = sets.getWeight();
            Integer repetitions = sets.getRepetitions();
            LocalDate dateOfWorkout = sets.getWorkout().getDateOfWorkout();
            PRStats prStats = new PRStats(weight, repetitions, dateOfWorkout);

            // if key/exerciseName doesn't exist in Map, create it value ExerciseStats(weight / repetitions)
            personalRecords.putIfAbsent(exerciseName, prStats);

            // if key exists, compare value(exerciseStats).getWeight() with current set weight and replace value with current exerciseStats if less
            personalRecords.computeIfPresent(exerciseName, (key, value) -> value.getWeight() >= weight ? value : prStats);
        });


        model.addAttribute("personalRecords", personalRecords);

        //Retrieve User's Height and dateOfBirth (to calculate age) from User object and auto inputs to Daily Health Profile Form
        User user = userService.loadUserByEmail(principal.getName());
        Integer age = Period.between(LocalDate.parse(user.getDateOfBirth()), LocalDate.now()).getYears();
        System.out.println("You are " + age + " years old.");

        model.addAttribute("height", user.getHeight());
        model.addAttribute("age", age);
        model.addAttribute("levelOne", "You are sedentary and do not exercise.");
        model.addAttribute("levelTwo", "You exercise lightly 1-3 times per week.");
        model.addAttribute("levelThree", "You exercise 3 - 5 days per week.");
        model.addAttribute("levelFour", "You exercise 6 - 7 days per week.");
        model.addAttribute("levelFive", "You exercise 7 days a week and also have a physically demanding job.");

        String[] weightGoals = {"Lose Weight", "Maintain Weight", "Gain Weight"};
        model.addAttribute("weightGoals", weightGoals);

        model.addAttribute("healthProfile", new HealthProfileDto());

        LocalDate today = LocalDate.now();
        List<HealthProfile> todayHealthProfiles = healthProfileService.findTodayProfiles(principal, today);
        System.out.println(todayHealthProfiles.size() > 0);

        if(todayHealthProfiles.size() > 0){
            model.addAttribute("dailyHp", "Click Below to Create a Meal or Workout");
        }

        return "index";
    }

    @PostMapping("/user/healthProfile")
    public String createHealthProfile(@ModelAttribute("healthProfile") HealthProfileDto healthProfileDto, Principal principal){
        String email = principal.getName();
        try {
            healthProfileService.save(healthProfileDto, principal);
        } catch (Exception e){
            System.out.println("The following error occurred when attempting to save a Health Profile to the Database: " + e.getMessage());
        }

        LOGGER.info("Health Profile for User: " + email + " has been created.");
        return "redirect:/profile";
    }

}


