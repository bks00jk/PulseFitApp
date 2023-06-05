package com.example.pulsefitapp.textUser;

import com.example.pulsefitapp.dto.SmsRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@org.springframework.stereotype.Service
public class SmsService {

    // using this interface instead of TwilioSmsSender class allows us to choose b/w implementations
    private final SmsSender smsSender;

    // @Qualifier relates to passing "twilio" into TwilioSmsSender @Service annotation
    @Autowired   // This is the actual twilio implementation
    public SmsService(@Qualifier("twilio") TwilioSmsSender smsSender) {
        this.smsSender = smsSender;
    }

    public void sendSms(SmsRequestDto smsRequestDto) {
        SmsRequest smsRequest = new SmsRequest(smsRequestDto.getPhoneNumber(), smsRequestDto.getMessage());
        try {
            smsSender.sendSms(smsRequest);
        } catch (Exception e){
            System.out.println("The following error occurred when attempting to send a text message to the User: " + e.getMessage());
        }

    }

}