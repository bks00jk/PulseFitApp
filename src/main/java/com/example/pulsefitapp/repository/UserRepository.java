package com.example.pulsefitapp.repository;

import com.example.pulsefitapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email); // Spring data JPA creates a query based on method name

   // All User Registration Data auto displayed in Profile Page Form; User can edit some or all of
   // this data, and click "Update" which sends all data in current form for updating User object.
    @Modifying
    @Transactional
    @Query("update User u set u.firstName = ?1, u.lastName = ?2, u.streetAddress = ?3, u.city = ?4, " +
            "u.zipCode = ?5, u.phone = ?6, u.height = ?7, u.dateOfBirth = ?8 where u.id = ?9")
    void updateUserById(String firstName, String lastName, String streetAddress, String city,
                        String zipCode, String phone, Float height, String dateOfBirth, Long id);


 @Override
    @Modifying
    @Transactional
    @Query("delete User u where u.id = ?1")
    void deleteById(Long aLong);




}
