package com.example.pulsefitapp.dto;


import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

// Data that user enters in Registration Page Form
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserRegistrationDto {

        @NotNull
        @NotEmpty
        private String firstName;

        @NotNull
        @NotEmpty
        private String lastName;

        private String streetAddress;
        private String city;
        private String zipCode;

        @NotNull
        @NotEmpty
        private String phone;

        @NotNull
        @NotEmpty
        @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
                flags = Pattern.Flag.CASE_INSENSITIVE)   // will consider valid if lower or uppercase letters used
        private String email;

        @NotNull
        @NotEmpty
        private String password;

        @NotNull
        @NotEmpty
        private Float height;

        @NotNull
        @NotEmpty
        private String dateOfBirth;



    public UserRegistrationDto(String firstName, String lastName, String streetAddress, String city,
                               String zipCode, String phone, String email, String password,
                               Float height, String dateOfBirth) {
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
        }

        // used to update user information on Profile Page
        public UserRegistrationDto(String firstName, String lastName, String streetAddress, String city,
                                   String zipCode, String phone, Float height, String dateOfBirth) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.streetAddress = streetAddress;
                this.city = city;
                this.zipCode = zipCode;
                this.phone = phone;
                this.height = height;
                this.dateOfBirth = dateOfBirth;

        }


}

