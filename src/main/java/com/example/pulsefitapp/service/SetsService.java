package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.SetsDto;
import com.example.pulsefitapp.model.Sets;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface SetsService {
    Sets save(SetsDto setsDto);
    List<Sets> loadSets();
    List<Sets> loadSetsByUserId(Principal principal);

    void deleteSet(Long id);
}
