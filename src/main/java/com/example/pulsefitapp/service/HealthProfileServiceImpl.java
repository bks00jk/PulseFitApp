package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.HealthProfileDto;
import com.example.pulsefitapp.model.HealthProfile;
import com.example.pulsefitapp.model.User;
import com.example.pulsefitapp.repository.HealthProfileRepository;
import com.example.pulsefitapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

// Saves Health Profile and Retrieves Health Profiles created Today
@Service
public class HealthProfileServiceImpl implements HealthProfileService{

    private final HealthProfileRepository healthProfileRepository;
    private final UserRepository userRepository;

    public HealthProfileServiceImpl(HealthProfileRepository healthProfileRepository, UserRepository userRepository) {
        this.healthProfileRepository = healthProfileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public HealthProfile save(HealthProfileDto healthProfileDto, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        LocalDate today = LocalDate.now();
        HealthProfile healthProfile = new HealthProfile(today, healthProfileDto.getWeight(), healthProfileDto.getExerciseLevel(), healthProfileDto.getTargetCalories(),
                healthProfileDto.getWeightGoal(), user);
        return healthProfileRepository.save(healthProfile);
    }

    // finds User's Health Profiles created today
    @Override
    public List<HealthProfile> findTodayProfiles(Principal principal, LocalDate today) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        return healthProfileRepository.findDailyByUserId(user.getId(), today);
    }



}
