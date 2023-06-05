package com.example.pulsefitapp.repository;

import com.example.pulsefitapp.model.Servings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ServingsRepository extends JpaRepository<Servings, Long> {

    @Override
    @Modifying
    @Transactional
    @Query("delete Servings s where s.id = ?1")
    void deleteById(Long aLong);


    @Query("from Servings s where s.id = ?1")
    Servings findServingsById(Long id);
}