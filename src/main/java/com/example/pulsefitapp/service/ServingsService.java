package com.example.pulsefitapp.service;

import com.example.pulsefitapp.dto.ServingsDto;
import com.example.pulsefitapp.model.Servings;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServingsService {
    Servings save(ServingsDto servingsDto);
    List<Servings> loadServings();

    void deleteServing(Long id);
}
