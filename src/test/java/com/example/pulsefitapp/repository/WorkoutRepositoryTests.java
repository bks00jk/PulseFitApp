package com.example.pulsefitapp.repository;

import com.example.pulsefitapp.model.User;
import com.example.pulsefitapp.model.Workout;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class WorkoutRepositoryTests {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testUpdateWorkout() {
        //creates a new "empty" Workout object w/2 fields: id, user_id and saves to DB
        Workout workout = new Workout(new User());
        Workout savedWorkout = workoutRepository.save(workout);

        //retrieves savedWorkout from DB by id
        Workout retrievedWorkout = workoutRepository.findWorkoutById(savedWorkout.getId());

        LocalDate today = LocalDate.now();

        //calls updateWorkoutById and injects remainder of Workout model data into constructor w/Workout id of retrieved existWorkout
        workoutRepository.updateWorkoutById(today, 30F, "Chest-Back-Biceps", retrievedWorkout.getId());


        //tests that the savedWorkout and existWorkout that was updated and retrieved has the same date
        Assertions.assertThat(retrievedWorkout.getDateOfWorkout()).isEqualTo(today);

    }


    @Test
    public void testRetrieveMostRecentWorkoutInDb() {
        //creates a new "empty" Workout object w/2 fields: id, user_id and saves to DB
        Workout Workout = new Workout(new User());
        Workout savedWorkout = workoutRepository.save(Workout);

        // retrieves most recent created Workout in DB
        Workout mostRecent = workoutRepository.findTopByOrderByIdDesc();


        Assertions.assertThat(savedWorkout.getId()).isEqualTo(mostRecent.getId());

    }

    @Test
    public void testFindAllWorkoutsByUserId() {

        User user = new User();
        User savedUser = userRepository.save(user);
        Workout Workout1 = new Workout(user);
        Workout savedWorkout1 = workoutRepository.save(Workout1);
        Workout Workout2 = new Workout(user);
        Workout savedWorkout2 = workoutRepository.save(Workout2);

        List<Workout> WorkoutList = workoutRepository.findAllByUserId(savedUser.getId());

        Assertions.assertThat(WorkoutList.size()).isEqualTo(2);

    }

    @Test
    public void testDeleteWorkoutById() {
        Workout Workout = new Workout();
        Workout savedWorkout = workoutRepository.save(Workout);

        workoutRepository.deleteWorkoutById(savedWorkout.getId());

        Workout findDeletedWorkout = workoutRepository.findWorkoutById(savedWorkout.getId());

        Assertions.assertThat(findDeletedWorkout).isNull();


    }

}
