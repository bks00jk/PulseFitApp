package com.example.pulsefitapp.controller;

import com.example.pulsefitapp.dto.FoodCategoryDto;
import com.example.pulsefitapp.model.FoodCategory;
import com.example.pulsefitapp.service.FoodCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

// Class contains routes to display all existing Food Categories in DB and to create/save a new Food Category
@Controller
@RequestMapping("/food/category")
public class FoodCategoryController {

    private final FoodCategoryService categoryService;

    @Autowired
    public FoodCategoryController(FoodCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String displayFoodCategoryPage(Model model) {
        model.addAttribute("category", new FoodCategoryDto()); // binds "category" attribute to the model

        List<FoodCategory> foodCategoryList =  categoryService.loadFoodCategories();
        model.addAttribute("categories", foodCategoryList);
        return "food_category";
    }

    @PostMapping
    public String saveCategory(@ModelAttribute("category") FoodCategoryDto categoryDto){
        try {
            categoryService.save(categoryDto);
        } catch (Exception e){
            System.out.println("The following error occurred when attempting to save a Food Category to the Database: " + e.getMessage());
        }

        return "redirect:/food/category?success";
    }
}

