package com.example.pulsefitapp.repository;

import com.example.pulsefitapp.model.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {

    FoodCategory findByName(String s);
}