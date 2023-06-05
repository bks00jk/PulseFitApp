package com.example.pulsefitapp.controller;


import com.example.pulsefitapp.dto.ExerciseDto;
import com.example.pulsefitapp.model.Exercise;
import com.example.pulsefitapp.model.ExerciseCategory;
import com.example.pulsefitapp.repository.ExerciseCategoryRepository;
import com.example.pulsefitapp.repository.ExerciseRepository;
import com.example.pulsefitapp.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

// Class contains routes to display all existing Exercises in DB and to create/save a new Exercise
@Controller
@RequestMapping("/exercise")
public class ExerciseController {


    private final ExerciseService exerciseService;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseCategoryRepository exerciseCategoryRepository;
    List<ExerciseCategory> exerciseCategoryList;



    @Autowired
    public ExerciseController(ExerciseService exerciseService, ExerciseCategoryRepository exerciseCategoryRepository, ExerciseRepository exerciseRepository) {
        this.exerciseService = exerciseService;
        this.exerciseCategoryRepository = exerciseCategoryRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @GetMapping
    public String displayExercisePage(Model model) {
        model.addAttribute("exercise", new ExerciseDto()); // binds "exercise" attribute to the model

        List<Exercise> exerciseList =  exerciseService.loadExercises();
        model.addAttribute("exercises", exerciseList);

        exerciseCategoryList = exerciseCategoryRepository.findAll();
        model.addAttribute("categories", exerciseCategoryList);

        return "exercise";
    }

    @PostMapping
    public String saveExercise(@ModelAttribute("exercise") ExerciseDto exerciseDto){
        try {
            exerciseService.save(exerciseDto);
        } catch (Exception e){
            System.out.println("The following error occurred when attempting to save an Exercise to the Database: " + e.getMessage());
        }

        return "redirect:/exercise?success";
    }
}

