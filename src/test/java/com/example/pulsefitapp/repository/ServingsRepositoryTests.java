package com.example.pulsefitapp.repository;


import com.example.pulsefitapp.model.Servings;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ServingsRepositoryTests {

    @Autowired
    private ServingsRepository servingsRepository;

    @Autowired
    private UserRepository userRepository;



    @Test
    public void testDeleteServingsById() {

        Servings servings = new Servings();
        Servings savedServings = servingsRepository.save(servings);

        servingsRepository.deleteById(servings.getId());

        Servings findDeletedServing = servingsRepository.findServingsById(servings.getId());

        Assertions.assertThat(findDeletedServing).isNull();


    }
}