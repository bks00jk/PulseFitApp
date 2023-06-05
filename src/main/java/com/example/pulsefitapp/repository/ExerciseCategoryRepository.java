package com.example.pulsefitapp.repository;

import com.example.pulsefitapp.model.ExerciseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseCategoryRepository extends JpaRepository<ExerciseCategory, Long>{

    ExerciseCategory findByName(String s);
}
