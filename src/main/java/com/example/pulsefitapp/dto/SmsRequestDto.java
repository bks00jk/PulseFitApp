package com.example.pulsefitapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SmsRequestDto {

    private String phoneNumber; // destination phone number
    private String message;

    public SmsRequestDto(String phoneNumber, String message){
        this.phoneNumber = phoneNumber;
        this.message = message;
    }
}
