package com.example.pulsefitapp.controller;

import com.example.pulsefitapp.model.User;
import com.example.pulsefitapp.model.Workout;
import com.example.pulsefitapp.service.UserService;
import com.example.pulsefitapp.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

//Class contains logic to display all User's Workouts in DB, create an empty Workout
// by clicking "Create a Workout" button on Homepage which redirects to Sets Page, and to delete a Workout.
@Controller
public class WorkoutController {

    private final WorkoutService workoutService;
    private final UserService userService;

    @Autowired
    public WorkoutController(WorkoutService workoutService, UserService userService) {
        this.workoutService = workoutService;
        this.userService = userService;
    }

    // displays all Workouts in DB for logged-in user
    @GetMapping("/workouts")
    public String displayWorkoutsPage(Model model, Principal principal){
        String email = principal.getName();
        User user = userService.loadUserByEmail(email);
        List<Workout> workouts = workoutService.loadWorkouts().stream().filter(wo -> wo.getUser().getId() == user.getId()).collect(Collectors.toList());

        model.addAttribute("workouts", workouts); // binds "workouts" attribute to the model right before page loads

        return "workouts";
    }

    @PostMapping("/workout/create")
    public String createWorkout(Principal principal) {
       try {
           workoutService.save(principal);
       } catch (Exception e){
           System.out.println("The following error occurred when attempting to create/save a Workout to the Database: " + e.getMessage());
       }

        return "redirect:/sets";

    }

    @PostMapping("/workout/delete/{workoutId}")
    public String deleteWorkout(@PathVariable Long workoutId){
        try {
            workoutService.deleteWorkout(workoutId);
        } catch (Exception e){
            System.out.println("The following error occurred when attempting to delete a Workout from the Database: " + e.getMessage());
        }

        return "redirect:/workouts";
    }


}
