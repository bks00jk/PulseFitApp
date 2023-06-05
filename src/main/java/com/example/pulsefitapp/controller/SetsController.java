package com.example.pulsefitapp.controller;


import com.example.pulsefitapp.dto.SetsDto;
import com.example.pulsefitapp.dto.SmsRequestDto;
import com.example.pulsefitapp.dto.WorkoutDto;
import com.example.pulsefitapp.model.*;
import com.example.pulsefitapp.repository.ExerciseRepository;
import com.example.pulsefitapp.repository.WorkoutRepository;
import com.example.pulsefitapp.service.SetsService;
import com.example.pulsefitapp.service.UserService;
import com.example.pulsefitapp.service.WorkoutService;
import com.example.pulsefitapp.textUser.SmsController;
import com.example.pulsefitapp.utilClass.PRStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Class creates/saves/displays all Sets User created for current Workout; Allows User to delete Sets
// Upon saving of Workout checks to see if any Set/Exercise has become new Personal Record for User and sends text message that occurs
@Controller
@RequestMapping
public class SetsController {

    private final SetsService setsService;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutService workoutService;
    private final WorkoutRepository workoutRepository;
    private final SmsController smsController;
    private final UserService userService;


    List<Exercise> exerciseList;
    List<Sets> setsList;


    @Autowired
    public SetsController(SetsService setsService, ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository,
                          WorkoutService workoutService, SmsController smsController, UserService userService) {
        this.setsService = setsService;
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
        this.workoutService = workoutService;
        this.smsController = smsController;
        this.userService = userService;
    }

    @GetMapping("/sets")
    public String displaySets(Model model) {
        model.addAttribute("set", new SetsDto()); // binds "sets" attribute to the model
        model.addAttribute("workout", new WorkoutDto()); // binds "workout" object
        // want to only display Sets that have just been created and linked to newly created Workout
        setsList = setsService.loadSets();
        Workout currentWorkout = workoutRepository.findTopByOrderByIdDesc();
        System.out.println(currentWorkout.getId());
        List<Sets> currentWorkoutSets = setsList.stream().filter(s -> s.getWorkout().getId() == currentWorkout.getId()).collect(Collectors.toList());

        model.addAttribute("sets", currentWorkoutSets);

        exerciseList = exerciseRepository.findAll();
        model.addAttribute("exercises", exerciseList);

        return "sets";
    }

    @PostMapping("/sets")
    public String saveSet(@ModelAttribute("sets") SetsDto setsDto) {
        try {
            setsService.save(setsDto);
        } catch (Exception e){
            System.out.println("The following error occurred when attempting to save a Sets object to the Database: " + e.getMessage());
        }

        return "redirect:/sets?success";
    }

    @PostMapping("/sets/delete/{setId}")
    public String deleteSet(@PathVariable Long setId) {
        try {
            setsService.deleteSet(setId);
        } catch (Exception e){
            System.out.println("The following error occurred when attempting to delete a Sets object from the Database: " + e.getMessage());
        }

        return "redirect:/sets";
    }

    @PostMapping("/workout/update")
    public String saveWorkout(@ModelAttribute("workout") WorkoutDto workoutDto, Principal principal) {
        String email = principal.getName();
        User user = userService.loadUserByEmail(email);

        // retrieves empty Workout created when User clicked "Create Workout" button and updates it with Sets just created.
        Workout currentWorkout = workoutRepository.findTopByOrderByIdDesc();

        try {
            workoutService.update(workoutDto, currentWorkout);
        } catch (Exception e){
            System.out.println("The following error occurred when attempting to update a Workout in the Database: " + e.getMessage());
        }


        //After saves a workout creates Personal Record Map by retrieving all of User's Sets in DB
        List<Sets> allSets = setsService.loadSetsByUserId(principal);

        // This instantiates a map of current Personal Records
        Map<String, PRStats> personalRecords = new HashMap<>();

        allSets.forEach(sets -> {
            String exerciseName = sets.getExercise().getName();
            Float weight = sets.getWeight();
            Integer repetitions = sets.getRepetitions();
            LocalDate dateOfWorkout = sets.getWorkout().getDateOfWorkout();
            Long workoutId = sets.getWorkout().getId();

            // PRStats is util class containing 4 fields (weight / repetitions / dateOfWorkout / workoutId)
            PRStats prStats = new PRStats(weight, repetitions, dateOfWorkout, workoutId);

            // If key/exerciseName doesn't exist in Map, create it with a value of prStats
            personalRecords.putIfAbsent(exerciseName, prStats);

            // If key exists, compare value(prStats).getWeight() with current Set weight and replace value with current prStats if less
            personalRecords.computeIfPresent(exerciseName, (key, value) -> value.getWeight() >= weight ? value : prStats);
        });

        // This instantiates a map of best weights for current workout
        Map<String, PRStats> workoutStats = new HashMap<>();
        currentWorkout.getSets().forEach(sets -> {
            String exerciseName = sets.getExercise().getName();
            Float weight = sets.getWeight();
            Integer repetitions = sets.getRepetitions();
            LocalDate dateOfWorkout = sets.getWorkout().getDateOfWorkout();
            Long workoutId = sets.getWorkout().getId();
            PRStats prStats = new PRStats(weight, repetitions, dateOfWorkout, workoutId);

            // If key/exerciseName doesn't exist in Map, create it with value prStats
            workoutStats.putIfAbsent(exerciseName, prStats);

            // If key exists, compare its value (prStats.getWeight()) with current Set's weight and replace value with prStats if less
            workoutStats.computeIfPresent(exerciseName, (key, value) -> value.getWeight() > weight ? value : prStats);
        });

        // Checks if each key/exercise of workoutStats exists in personalRecords;
        // If it exists, check to see if value's weight field in workoutStats is > weight field in personalRecords.
        // If it is, a new PR has been reached, smsController.sendSms method is called, and a text message sent to user w/in 3-4 seconds.
        workoutStats.forEach((k,v) -> {
            if(personalRecords.containsKey(k) && (workoutStats.get(k).getWeight() >= personalRecords.get(k).getWeight())
                    && (currentWorkout.getId() == personalRecords.get(k).getWorkoutId())){
                String message = "Hey, " + user.getFirstName() +"! You just reached a new Personal Record for " + k +
                        " with a weight of " + workoutStats.get(k).getWeight() + " lbs for " + workoutStats.get(k).getRepetitions() +
                        " repetitions. Keep up the great work!!!";
                String phoneNumber = user.getPhone();
                SmsRequestDto messageUser = new SmsRequestDto(phoneNumber, message);

                try {
                    smsController.sendSms(messageUser);
                } catch (Exception e){
                    System.out.println("The following error occurred when attempting to send a text message to the User: " + e.getMessage());
                }

            }
        });

        return "redirect:/workouts";
    }
}