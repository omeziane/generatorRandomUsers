package com.generator.user.app.controller;

import com.generator.user.app.common.UploadUsersResponse;
import com.generator.user.app.dto.UserGeneratorDTO;
import com.generator.user.app.dto.UserInsertDTO;
import com.generator.user.app.dto.UserSearchDTO;
import com.generator.user.app.dto.UserUpdateDTO;
import com.generator.user.app.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserGeneratorControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserGeneratorController userController;

    private int count;

    @BeforeEach
    public void setup() {
        count = 10;
    }

    @Test
    public void testGenerateUsers() {
        // Mock the service response
        List<UserGeneratorDTO> mockUsers = createMockUsers();
        when(userService.generateRandomUsers(count)).thenReturn(mockUsers);

        // Invoke the controller method
        ResponseEntity<List<UserGeneratorDTO>> response = userController.generateUsers(count);

        // Verify the service method was called
        verify(userService, times(1)).generateRandomUsers(count);

        // Verify the response headers
        HttpHeaders headers = response.getHeaders();
        String contentDisposition = headers.getFirst(HttpHeaders.CONTENT_DISPOSITION);
        assert contentDisposition != null;
        assert contentDisposition.contains("attachment");
        assert contentDisposition.contains("filename=users.json");

        // Verify the response body
        List<UserGeneratorDTO> responseBody = response.getBody();
        assert responseBody != null;
        assert responseBody.size() == mockUsers.size();
        for (int i = 0; i < responseBody.size(); i++) {
            UserGeneratorDTO actual = responseBody.get(i);
            UserGeneratorDTO expected = mockUsers.get(i);
            assertUserGeneratorDTO(actual, expected);
        }
    }

    private List<UserGeneratorDTO> createMockUsers() {
        List<UserGeneratorDTO> mockUsers = new ArrayList<>();

        // Create and add mock users to the list
        UserGeneratorDTO user1 = new UserGeneratorDTO();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        // Set other attributes...
        mockUsers.add(user1);

        UserGeneratorDTO user2 = new UserGeneratorDTO();
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        // Set other attributes...
        mockUsers.add(user2);

        // Add more mock users as needed...

        return mockUsers;
    }

    private void assertUserGeneratorDTO(UserGeneratorDTO actual, UserGeneratorDTO expected) {
        assert actual.getFirstName().equals(expected.getFirstName());
        assert actual.getLastName().equals(expected.getLastName());
        // Assert other attributes...
    }

    @Test
    public void uploadUsersFileTest() throws IOException {
        // Create a mock UserController object
        UserGeneratorController userGeneratorController = Mockito.mock(UserGeneratorController.class);
        // Create a mock MultipartFile
        MockMultipartFile mockFile = new MockMultipartFile("file", "users.json",
                "application/json", getMockJson().getBytes());

        // Mock the processUploadedFile method to return a list of user DTOs
        List<UserInsertDTO> mockUsers = new ArrayList<>();
        // Add the existing mock users to the list
        mockUsers.add(new UserInsertDTO("John", "Doe", new Date(1234567890L), "New York", "US", "https://example.com/avatar.jpg", "ABC Ltd", "Software Engineer", "1234567890", "jdoe", "johndoe@example.com", "password", "user"));
        mockUsers.add(new UserInsertDTO("Jane", "Smith", new Date(9876543210L), "London", "GB", "https://example.com/avatar.jpg", "XYZ Corp", "Product Manager", "9876543210", "jsmith", "janesmith@example.com", "password", "admin"));

        // Stub the processUploadedFile method on the userService mock
        //when(userGeneratorController.processUploadedFile(mockFile)).thenReturn(mockUsers);

        // Test the uploadUsersFile method
        ResponseEntity<Object> response = userController.uploadUsersFile(mockFile);

        // Assert the response status code
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert the response body
        UploadUsersResponse responseBody = (UploadUsersResponse) response.getBody();
        assertEquals(0, responseBody.getTotalRecords());
        assertEquals(0, responseBody.getRecordsImported());
        assertEquals(0, responseBody.getRecordsFailed());
        assertEquals("No exception occurred", responseBody.getMessage());
    }

    private String getMockJson() {
        return "{\n" +
                "    \"users\": [\n" +
                "        {\n" +
                "            \"firstName\": \"Alice\",\n" +
                "            \"lastName\": \"Brown\",\n" +
                "            \"birthDate\": -1307526241092,\n" +
                "            \"city\": \"Berlin\",\n" +
                "            \"country\": \"MA\",\n" +
                "            \"avatar\": \"https://example.com/avatar.jpg\",\n" +
                "            \"company\": \"Widgets Co\",\n" +
                "            \"jobPosition\": \"CEO\",\n" +
                "            \"mobile\": \"9157504263\",\n" +
                "            \"username\": \"abrown\",\n" +
                "            \"email\": \"alice.brown@hotmail.com\",\n" +
                "            \"password\": \"zocsVY\",\n" +
                "            \"role\": \"user\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"firstName\": \"Alice\",\n" +
                "            \"lastName\": \"Brown\",\n" +
                "            \"birthDate\": -1307526241092,\n" +
                "            \"city\": \"Berlin\",\n" +
                "            \"country\": \"MA\",\n" +
                "            \"avatar\": \"https://example.com/avatar.jpg\",\n" +
                "            \"company\": \"Widgets Co\",\n" +
                "            \"jobPosition\": \"CEO\",\n" +
                "            \"mobile\": \"9157504263\",\n" +
                "            \"username\": \"abrown\",\n" +
                "            \"email\": \"alice.brown@hotmail.com\",\n" +
                "            \"password\": \"zocsVY\",\n" +
                "            \"role\": \"user\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }

    @Test
    public void testGet() {
        // Create a list of mock UserSearchDTO objects
        List<UserSearchDTO> mockUsers = new ArrayList<>();
        // Add mock UserSearchDTO objects to the list

        // Stub the userService.findAllEntity() method to return the mock users
        Mockito.when(userService.findAllEntity()).thenReturn(mockUsers);

        // Invoke the controller method
        List<UserSearchDTO> response = userController.get(null);

        // Verify the userService.findAllEntity() method was called
        Mockito.verify(userService, Mockito.times(1)).findAllEntity();

        // Assert the response
        Assert.assertEquals(mockUsers, response);
    }

    @Test
    public void testInsertUser() {
        // Create a mock UserInsertDTO object
        UserInsertDTO mockUser = new UserInsertDTO("John", "Doe", new Date(), "New York", "US", "https://example.com/avatar.jpg", "ABC Ltd", "Software Engineer", "1234567890", "jdoe", "johndoe@example.com", "password", "user");

        // Mock the behavior of userService.insertEntity() to verify the passed argument
        ArgumentCaptor<UserInsertDTO> argumentCaptor = ArgumentCaptor.forClass(UserInsertDTO.class);
        Mockito.doNothing().when(userService).insert(argumentCaptor.capture());

        // Invoke the controller method
        userController.insert(null, mockUser); // Pass null as the token

        // Verify the userService.insertEntity() method was called
        Mockito.verify(userService, Mockito.times(1)).insert(argumentCaptor.getValue());

        // Assert the captured UserInsertDTO object
        UserInsertDTO capturedUser = argumentCaptor.getValue();
        Assert.assertEquals(mockUser, capturedUser);
    }

    @Test
    public void testUpdateUser() {
        // Mock the request DTO
        UserUpdateDTO requestDto = new UserUpdateDTO();
        // Set the properties of the request DTO

        // Mock the service response
        // Set up any necessary mocks or stubs for the service method

        // Invoke the controller method
        userController.update(null, requestDto);

        // Verify the service method was called
        verify(userService, times(1)).update(requestDto);

    }

    @Test
    public void testDeleteUser() {
        // Mock the request parameter
        Long userId = 123L;

        // Mock the service response
        // Set up any necessary mocks or stubs for the service method

        // Invoke the controller method
        userController.delete(null, userId);

        // Verify the service method was called
        verify(userService, times(1)).delete(userId);

        // Assert the response body or any other assertions
        // Add assertions based on the expected behavior of the controller method
    }

    @Test
    public void testGetUserProfile() {
        // Mock the path variable
        String username = "admin";

        // Create a mock UserSearchDTO
        UserSearchDTO mockUserProfileAdmin = new UserSearchDTO(1L, "John", "Doe", new Date(), "New York", "US", "https://example.com/avatar.jpg", "ABC Ltd", "Software Engineer", "1234567890", "jdoe", "johndoe@example.com", "password", "admin");;
        UserSearchDTO mockUserProfileJohn = new UserSearchDTO(2L, "John", "Smith", new Date(), "New York", "US", "https://example.com/avatar.jpg", "ABC Ltd", "Software Engineer", "1234567890", "jdoe", "johndoe@example.com", "password", "user");;

        // Mock the authentication context
        UserDetails userDetailsAdmin = User.builder()
                .username("admin")
                .password("password")
                .roles("admin")
                .build();
        Authentication authenticationAdmin = new UsernamePasswordAuthenticationToken(userDetailsAdmin, null);

        UserDetails userDetailsJohn = User.builder()
                .username("john")
                .password("password")
                .roles("user")
                .build();
        Authentication authenticationJohn = new UsernamePasswordAuthenticationToken(userDetailsJohn, null);

        // Set the authentication context
        SecurityContextHolder.getContext().setAuthentication(authenticationAdmin);

        // Stub the getUserProfile method on the userService mock for admin user
        Mockito.when(userService.getUserProfile("admin")).thenReturn(mockUserProfileAdmin);

        // Invoke the controller method for admin user
        ResponseEntity<UserSearchDTO> responseAdmin = userController.getUserProfile(username);

        // Verify the service method was called for admin user
        verify(userService, times(2)).getUserProfile("admin");

        // Assert the response for admin user
        assertEquals(HttpStatus.OK, responseAdmin.getStatusCode());
        assertEquals(mockUserProfileAdmin, responseAdmin.getBody());

        // Set the authentication context to John user
        SecurityContextHolder.getContext().setAuthentication(authenticationJohn);

        // Stub the getUserProfile method on the userService mock for John user
        Mockito.when(userService.getUserProfile("john")).thenReturn(mockUserProfileJohn);

        // Invoke the controller method for John user
        ResponseEntity<UserSearchDTO> responseJohn = userController.getUserProfile("john");

        // Verify the service method was called for John user
        verify(userService, times(2)).getUserProfile("john");

        // Assert the response for John user
        assertEquals(HttpStatus.OK, responseJohn.getStatusCode());
        assertEquals(mockUserProfileJohn, responseJohn.getBody());
    }

    @Test
    public void testGetUserProfile_Unauthorized() {
        // Mock the path variable
        String username = "unauthorized";

        // Mock the authentication context
        UserDetails userDetailsAdmin = User.builder()
                .username("admin")
                .password("password")
                .roles("admin")
                .build();
        Authentication authenticationAdmin = new UsernamePasswordAuthenticationToken(userDetailsAdmin, null);

        // Set the authentication context to admin user
        SecurityContextHolder.getContext().setAuthentication(authenticationAdmin);

        // Stub the getUserProfile method on the userService mock for admin user
        UserSearchDTO mockUserProfileAdmin = new UserSearchDTO(1L, "John", "Doe", new Date(), "New York", "US", "https://example.com/avatar.jpg", "ABC Ltd", "Software Engineer", "1234567890", "jdoe", "johndoe@example.com", "password", "unauthorized");;
        Mockito.when(userService.getUserProfile("admin")).thenReturn(mockUserProfileAdmin);

        // Invoke the controller method for unauthorized user
        ResponseEntity<UserSearchDTO> responseUnauthorized = userController.getUserProfile(username);

        // Verify the service method was not called for unauthorized user
        //verify(userService, times(0)).getUserProfile(username);

        // Assert the response for unauthorized user
        assertEquals(HttpStatus.UNAUTHORIZED, responseUnauthorized.getStatusCode());
        assertNull(responseUnauthorized.getBody());

        // Reset the authentication context
        SecurityContextHolder.clearContext();
    }
}
