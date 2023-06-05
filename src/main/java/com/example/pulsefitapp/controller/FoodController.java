package com.example.pulsefitapp.controller;

import com.example.pulsefitapp.dto.FoodDto;
import com.example.pulsefitapp.model.Food;
import com.example.pulsefitapp.model.FoodCategory;
import com.example.pulsefitapp.repository.FoodCategoryRepository;
import com.example.pulsefitapp.repository.FoodRepository;
import com.example.pulsefitapp.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

// Class contains routes/logic to display all Food objects stored in DB and to create/save a new Food object.
@Controller
@RequestMapping("/food")
public class FoodController {

    private final FoodService foodService;
    private final FoodRepository foodRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    List<FoodCategory> foodCategoryList;


    @Autowired
    public FoodController(FoodService foodService, FoodRepository foodRepository, FoodCategoryRepository foodCategoryRepository) {
        this.foodService = foodService;
        this.foodRepository = foodRepository;
        this.foodCategoryRepository = foodCategoryRepository;
    }

    @GetMapping
    public String displayFoodPage(Model model) {
        model.addAttribute("food", new FoodDto()); // binds "food" attribute to the model

        List<Food> foodList =  foodService.loadFood();
        model.addAttribute("foodBag", foodList);

        foodCategoryList = foodCategoryRepository.findAll();
        model.addAttribute("categories", foodCategoryList);

        return "food";
    }

    @PostMapping
    public String saveFood(@ModelAttribute("food") FoodDto foodDto){
        try {
            foodService.save(foodDto);
        } catch (Exception e){
            System.out.println("The following error occurred when attempting to save a Food to the Database: " + e.getMessage());
        }
        return "redirect:/food?success";
    }
}




