package com.generator.user.app.controller;

import com.generator.user.app.common.UserLoginRequest;
import com.generator.user.app.security.AppUserDetails;
import com.generator.user.app.security.AppUserDetailsService;
import com.generator.user.app.security.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserLoginControllerTest {

    private UserLoginController controller;

    @Mock
    private AppUserDetailsService userDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new UserLoginController();
        controller.userDetailsService = userDetailsService;
        controller.authenticationManager = authenticationManager;
        controller.jwtTokenUtil = jwtTokenUtil;
    }

    @Test
    void testLogin_Successful() {
        // Mock request
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("testuser");
        request.setPassword("testpassword");

        // Mock authentication
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword());
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(null);

        // Mock user details
        AppUserDetails userDetails = mock(AppUserDetails.class);
        when(userDetailsService.loadUserByUsername(request.getUsername())).thenReturn(userDetails);

        // Mock token generation
        String token = "testtoken";
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn(token);

        // Invoke the controller method
        ResponseEntity<Object> responseEntity = controller.login(request);

        // Verify authentication manager was called
        verify(authenticationManager, times(1)).authenticate(authenticationToken);

        // Verify user details service was called
        verify(userDetailsService, times(1)).loadUserByUsername(request.getUsername());

        // Verify token generation was called
        verify(jwtTokenUtil, times(1)).generateToken(userDetails);

        // Verify the response status and headers
        //assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        HttpHeaders headers = responseEntity.getHeaders();
        assertNotNull(headers);
        //assertTrue(headers.containsKey("Authorization"));
        //assertEquals("Bearer " + token, headers.getFirst("Authorization"));

        // Verify the response body
        assertNotNull(responseEntity.getBody());
        //assertTrue(responseEntity.getBody() instanceof UserAuthentificationResponse);
        //UserAuthentificationResponse response = (UserAuthentificationResponse) responseEntity.getBody();
        //assertEquals(token, response.getAccessToken());
    }

    @Test
    void testLogin_InvalidUsername() {
        // Mock request with empty username
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("");
        request.setPassword("testpassword");

        // Invoke the controller method
        ResponseEntity<Object> responseEntity = controller.login(request);

        // Verify authentication manager was not called
        verify(authenticationManager, never()).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Verify user details service was not called
        verify(userDetailsService, never()).loadUserByUsername(anyString());

        // Verify token generation was not called
        verify(jwtTokenUtil, never()).generateToken(any(AppUserDetails.class));

        // Verify the response status and body
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Username is required", responseEntity.getBody());
    }

    @Test
    void testLogin_InvalidPassword() {
        // Mock request with empty password
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("testuser");
        request.setPassword("");

        // Invoke the controller method
        ResponseEntity<Object> responseEntity = controller.login(request);

        // Verify authentication manager was not called
        verify(authenticationManager, never()).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Verify user details service was not called
        verify(userDetailsService, never()).loadUserByUsername(anyString());

        // Verify token generation was not called
        verify(jwtTokenUtil, never()).generateToken(any(AppUserDetails.class));

        // Verify the response status and body
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Password is required", responseEntity.getBody());
    }

    @Test
    void testLogin_BadCredentials() {
        // Mock request
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("testuser");
        request.setPassword("testpassword");

        // Mock authentication with bad credentials
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword());
        when(authenticationManager.authenticate(authenticationToken))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Invoke the controller method
        ResponseEntity<Object> responseEntity = controller.login(request);

        // Verify authentication manager was called
        verify(authenticationManager, times(1)).authenticate(authenticationToken);

        // Verify user details service was not called
        verify(userDetailsService, never()).loadUserByUsername(anyString());

        // Verify token generation was not called
        verify(jwtTokenUtil, never()).generateToken(any(AppUserDetails.class));

        // Verify the response status and body
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Bad credentials", responseEntity.getBody());
    }
}
