package com.generator.user.app.repository;

import com.generator.user.app.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testAddUser() {
        // Create a new user
        User user = new User();
        user.setUsername("john");
        user.setPassword("password");
        // Set other user properties as needed

        // Save the user to the database
        User savedUser = userRepository.save(user);

        // Perform assertions or additional operations
        // to verify the user has been successfully added
        // For example:
        assertNotNull(savedUser.getId());
        assertEquals("john", savedUser.getUsername());
        // Add more assertions as needed
    }
}
