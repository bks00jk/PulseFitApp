package com.example.pulsefitapp.textUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsRequest {

    // User/Destination phone number
    private String phoneNumber;
    private String message;

    public SmsRequest(String phoneNumber, String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public SmsRequest(){

    }


    @Override
    public String toString() {
        return "SmsRequest{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
