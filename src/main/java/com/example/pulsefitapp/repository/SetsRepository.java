package com.example.pulsefitapp.repository;

import com.example.pulsefitapp.model.Sets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SetsRepository extends JpaRepository<Sets, Long> {

    // Retrieves all Sets from DB linked to a specific User through Workout Entity connection
    @Query("from Sets s join Workout w on s.workout.id = w.id where w.user.id = ?1")
    List<Sets> findAllByUserId(Long id);

    @Override
    @Modifying
    @Transactional
    @Query("delete Sets s where s.id = ?1")
    void deleteById(Long aLong);


}