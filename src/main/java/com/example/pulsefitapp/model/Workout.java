package com.example.pulsefitapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "workout")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "workout_name")
    private String workoutName;

    @Column(name = "date_of_workout")
    private LocalDate dateOfWorkout;

    @Column(name = "duration")
    private Float duration;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "workout", fetch = FetchType.EAGER)
    private Set<Sets> sets = new HashSet<>();


    public Workout(String workoutName, LocalDate dateOfWorkout, Float duration, User user, Set<Sets> sets) {
        this.workoutName = workoutName;
        this.dateOfWorkout = dateOfWorkout;
        this.duration = duration;
        this.user = user;
        this.sets = sets;
    }

    public Workout(User user){
        this.user = user;
    }

}
