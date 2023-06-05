package com.example.pulsefitapp.controller;

import com.example.pulsefitapp.dto.UserRegistrationDto;
import com.example.pulsefitapp.exception.UserAlreadyExistsException;
import com.example.pulsefitapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

// Class contains routes/logic to display Registration page, allows User to Register, update and delete their profile.
@Controller
public class UserRegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationController.class);
    private final UserService userService;

    @Autowired
    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String displayRegistrationPage(Model model){
        model.addAttribute("user", new UserRegistrationDto()); // binds "user" attribute to the model right before page loads
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") UserRegistrationDto userRegistrationDto, Model model) throws UserAlreadyExistsException {
        try{
            userService.save(userRegistrationDto);
            // redirects User to registration?failed which displays a message that Email already Exists for a User
        } catch (UserAlreadyExistsException e){
            return "redirect:/registration?failed";
        }

        LOGGER.info("User " + userRegistrationDto.getEmail() + " has been created.");
         return "login";
    }

    @PostMapping("/profile/update")
    public String updateUser(@ModelAttribute("user") UserRegistrationDto userRegistrationDto, Principal principal){
        try {
            userService.update(userRegistrationDto, principal);
        } catch (Exception e){
            System.out.println("The following error occurred when attempting to update the User's Profile (User object) in the Database: " + e.getMessage());
        }

        return "redirect:/profile";
    }

    @PostMapping("/profile/delete")
    public String deleteUser(Principal principal){
        try {
            userService.delete(principal);
        } catch (Exception e){
            System.out.println("The following error occurred when attempting to delete a User from the Database: " + e.getMessage());
        }

        return "redirect:/login?deleted";
    }


}
