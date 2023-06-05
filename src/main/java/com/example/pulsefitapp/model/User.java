package com.example.pulsefitapp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "street_address")
    private String streetAddress;

    private String city;

    @Column(name = "zip_code")
    private String zipCode;

    // Twilio text takes phone as String
    private String phone;
    private String email;
    private String password;
    private Float height;


    @Column(name = "date_of_birth")
    private String dateOfBirth;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // when we retrieve user, we retrieve all associated roles
    @JoinTable(
            name= "user_role",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name ="role_id", referencedColumnName = "id"
            )
    )
    private Collection<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Workout> workout = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Meal> meal = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<HealthProfile> healthProfiles = new HashSet<>();


   public User(String firstName, String lastName, String streetAddress, String city,
                String zipCode, String phone, String email, String password,
                Float height, String dateOfBirth, Collection<Role> roles, Set<Workout> workout,
                Set<Meal> meal, Set<HealthProfile> healthProfiles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddress = streetAddress;
        this.city = city;
        this.zipCode = zipCode;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.height = height;
        this.dateOfBirth = dateOfBirth;
        this.roles = roles;
        this.workout = workout;
        this.meal = meal;
        this.healthProfiles = healthProfiles;
    }


}
