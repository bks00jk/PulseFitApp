package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.HealthProfileDto;
import com.example.pulsefitapp.model.HealthProfile;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

public interface HealthProfileService {
    HealthProfile save(HealthProfileDto healthProfileDto, Principal principal);

    List<HealthProfile> findTodayProfiles(Principal principal, LocalDate today);
}
