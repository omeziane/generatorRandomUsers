package com.generator.user.app.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest
@SpringJUnitConfig
class JwtTokenUtilTest {

    @Value("${com.sdf.jwt.secret}")
    private String secret;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    public void setUp() {
        jwtTokenUtil.setSecret(secret);
    }

    @Test
    void testGetUsernameFromToken() {
        // Create a mock UserDetails object or provide real implementation as needed
        UserDetails userDetails = Mockito.mock(UserDetails.class);

        String token = jwtTokenUtil.generateToken(userDetails);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Assertions.assertNull(username);
        
    }

    @Test
    void testGenerateToken() {
        // Create a mock UserDetails object or provide real implementation as needed
        UserDetails userDetails = Mockito.mock(UserDetails.class);

        String token = jwtTokenUtil.generateToken(userDetails);
        Assertions.assertNotNull(token);
        
    }

}


