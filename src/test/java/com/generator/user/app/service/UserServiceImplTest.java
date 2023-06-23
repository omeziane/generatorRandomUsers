package com.generator.user.app.service;

import com.generator.user.app.dto.UserGeneratorDTO;
import com.generator.user.app.dto.UserInsertDTO;
import com.generator.user.app.dto.UserSearchDTO;
import com.generator.user.app.entity.User;
import com.generator.user.app.repository.UserRepository;
import com.generator.user.app.utils.PasswordUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllEntity() {
        List<UserSearchDTO> userList = Arrays.asList(new UserSearchDTO(), new UserSearchDTO());
        when(repository.findAllEntity()).thenReturn(userList);

        List<UserSearchDTO> result = userService.findAllEntity();

        assertEquals(2, result.size());
        Mockito.verify(repository).findAllEntity();
    }

    @Test
    void testInsert() {
        UserInsertDTO userDTO = new UserInsertDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");

        String password = "myPassword";
        String hashedPassword = PasswordUtils.hashPasswordMD5(password);
        userDTO.setPassword(hashedPassword);

        when(repository.save(Mockito.any(User.class))).thenReturn(new User());

        userService.insert(userDTO);

        Mockito.verify(repository).save(Mockito.any(User.class));
    }

    @Test
    void testDelete() {
        Long userId = 1L;

        userService.delete(userId);

        Mockito.verify(repository).deleteById(userId);
    }

    @Test
    void testGenerateRandomUsers() {
        int count = 3;
        List<UserGeneratorDTO> users = userService.generateRandomUsers(count);

        assertEquals(count, users.size());
        for (UserGeneratorDTO user : users) {
            Assertions.assertNotNull(user.getFirstName());
            Assertions.assertNotNull(user.getLastName());
            Assertions.assertNotNull(user.getBirthDate());
            Assertions.assertNotNull(user.getCity());
            Assertions.assertNotNull(user.getCountry());
            Assertions.assertNotNull(user.getAvatar());
            Assertions.assertNotNull(user.getCompany());
            Assertions.assertNotNull(user.getJobPosition());
            Assertions.assertNotNull(user.getMobile());
            Assertions.assertNotNull(user.getUsername());
            Assertions.assertNotNull(user.getEmail());
            Assertions.assertNotNull(user.getPassword());
            Assertions.assertNotNull(user.getRole());
        }
    }

    @Test
    void testGetUserProfile() {
        String username = "johndoe";
        UserSearchDTO userDTO = new UserSearchDTO();
        when(repository.findByUsername(username)).thenReturn(userDTO);

        UserSearchDTO result = userService.getUserProfile(username);

        assertEquals(userDTO, result);
        Mockito.verify(repository).findByUsername(username);
    }
}

