package com.example.pulsefitapp.repository;

import com.example.pulsefitapp.model.HealthProfile;
import com.example.pulsefitapp.model.User;
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
public class HealthProfileRepositoryTests {

    @Autowired
    private HealthProfileRepository healthProfileRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testFindNewestHealthProfileInDb() {

        HealthProfile healthProfile1 = new HealthProfile();
        HealthProfile healthProfile2 = new HealthProfile();
        HealthProfile savedHealthProfile1 = healthProfileRepository.save(healthProfile1);
        HealthProfile savedHealthProfile2 = healthProfileRepository.save(healthProfile2);

        HealthProfile newestHealthProfileInDb = healthProfileRepository.findTopByOrderByIdDesc();

        Assertions.assertThat(newestHealthProfileInDb.getId()).isEqualTo(savedHealthProfile2.getId());
    }

    @Test
    public void testFindDailyHealthProfilesByUserId() {

        User user = new User();
        User savedUser = userRepository.save(user);

        HealthProfile healthProfile1 = new HealthProfile();
        HealthProfile healthProfile2 = new HealthProfile();

        LocalDate today = LocalDate.now();
        healthProfile1.setUser(savedUser);
        healthProfile1.setDate(today);
        healthProfile2.setUser(savedUser);
        healthProfile2.setDate(today);

        HealthProfile savedHealthProfile1 = healthProfileRepository.save(healthProfile1);
        HealthProfile savedHealthProfile2 = healthProfileRepository.save(healthProfile2);

        List<HealthProfile> userDailyHealthProfiles = healthProfileRepository.findDailyByUserId(savedUser.getId(), today);

        Assertions.assertThat(userDailyHealthProfiles.size()).isEqualTo(2); // db confirms 2 health profiles for new user w/id 63 and today's date
    }
}