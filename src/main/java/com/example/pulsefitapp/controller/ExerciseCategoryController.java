package com.example.pulsefitapp.controller;

import com.example.pulsefitapp.dto.ExerciseCategoryDto;
import com.example.pulsefitapp.model.ExerciseCategory;
import com.example.pulsefitapp.service.ExerciseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

// Class contains routes to display all existing Exercise Categories to screen and to create/save a new EC.
@Controller
@RequestMapping("/exercise/category")
public class ExerciseCategoryController {

    private final ExerciseCategoryService categoryService;

    @Autowired
    public ExerciseCategoryController(ExerciseCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public String displayExerciseCategoryPage(Model model) {
        model.addAttribute("category", new ExerciseCategoryDto()); // binds "category" attribute to the model

        List<ExerciseCategory> exerciseCategoryList =  categoryService.loadExerciseCategories();
        model.addAttribute("categories", exerciseCategoryList);
        return "exercise_category";
    }

    @PostMapping
    public String saveCategory(@ModelAttribute("category") ExerciseCategoryDto categoryDto){
        try {
            categoryService.save(categoryDto);
        } catch (Exception e){
            System.out.println("The following error occurred when attempting to save an Exercise Category to the Database: " + e.getMessage());
        }

        return "redirect:/exercise/category?success";
    }
}
