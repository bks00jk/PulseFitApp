package com.example.pulsefitapp.repository;

import com.example.pulsefitapp.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    //Retrieves Workout object most recently created in DB overall
    Workout findTopByOrderByIdDesc();

    // Updates Workout with User inputted info in Save Workout Form, after Sets created/link to Workout
    @Modifying
    @Transactional
    @Query("update Workout w set w.dateOfWorkout = ?1, w.duration = ?2, w.workoutName = ?3 where w.id = ?4")
    void updateWorkoutById(LocalDate dateOfWorkout, Float duration, String workoutName, Long id);

    @Query("from Workout w where w.user.id = ?1")
    List<Workout> findAllByUserId(Long id);

    @Query("from Workout w where w.id = ?1")
    Workout findWorkoutById(Long id);

    @Modifying
    @Transactional
    @Query("delete Workout w where w.id = ?1")
    void deleteWorkoutById(Long id);

    }

