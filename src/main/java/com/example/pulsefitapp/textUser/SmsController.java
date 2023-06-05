package com.example.pulsefitapp.textUser;

import com.example.pulsefitapp.dto.SmsRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// sendSms conditionally called directly from SetsController and ServingsController when User Saves a Workout
// that contains a new Personal Record OR when User saves a Meal whose calories total causes daily total calories
// consumed amount to exceed daily target calories amount calculated in Daily Health Profile form each day.
@Controller
@RequestMapping("api/v1/sms")
public class SmsController {
    private final SmsService service;

    @Autowired
    public SmsController(SmsService service) {
        this.service = service;
    }

    @PostMapping
    public void sendSms(SmsRequestDto smsRequestDto){
        service.sendSms(smsRequestDto);
    }



}

