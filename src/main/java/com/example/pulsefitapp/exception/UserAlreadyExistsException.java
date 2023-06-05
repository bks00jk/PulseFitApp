package com.example.pulsefitapp.exception;

// custom exception thrown if new User attempts to register with email address used by another User
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String s){
        super(s);
    }
}
