package com.example.pulsefitapp.service;

import com.example.pulsefitapp.textUser.SmsRequest;
import com.example.pulsefitapp.textUser.TwilioSmsSender;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.aspectj.bridge.MessageUtil.fail;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TwilioSmsSenderTests {


    @Autowired
    private TwilioSmsSender smsSender;

    // First 2 Tests pass; catch block/fail not reached AND first 2 Twilio verified numbers actually received the text message.
    // Logging.info message re: texts sent (phone number + message) appear in console
    // 3rd test only fails b/c I didn't add the number as a verified # on Twilio for my trial account;
    // com.twilio.exception.ApiException: The number  is unverified. Trial accounts cannot send messages to unverified numbers; verify  at twilio.com/user/account/phone-numbers/verified, or purchase a Twilio number to send messages to unverified numbers.
    // However, 3rd test did not hit catch block for having illegal arguments as SmsRequest constructor contained valid phone # ( it passed regex validation method) and message.
    @ParameterizedTest
    @ValueSource(strings = {"7814678117", "8572253921", "7817841958"})
    public void testSendSms(String phoneNumber) {
        try {
            SmsRequest smsRequest = new SmsRequest(phoneNumber, "Hello from SmsServiceTests.");
            smsSender.sendSms(smsRequest);
        } catch (IllegalArgumentException e) {
            fail("Should not have thrown any exception since valid phone Number and message inputted.");
        }

    }
}