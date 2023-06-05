package com.example.pulsefitapp.repository;

import com.example.pulsefitapp.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Exercise findByName(String s);
}
