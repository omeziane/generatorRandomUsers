package com.generator.user.app.utils;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoleUtilsTest {

    @Test
    void testHasRoleAdmin_withAdminRole() {
        // Create a mock Authentication object
        Authentication authenticationMock = mock(Authentication.class);

        // Create a single authority "ROLE_ADMIN"
        GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");

        // Set the authorities on the mock
        when(authenticationMock.getAuthorities()).thenAnswer(invocation -> Collections.singletonList(adminAuthority));

        // Call the method under test
        boolean hasAdminRole = RoleUtils.hasRoleAdmin(authenticationMock);

        // Verify the result
        assertTrue(hasAdminRole);
    }

    @Test
    void testHasRoleAdmin_withoutAdminRole() {
        // Create a mock Authentication object
        Authentication authenticationMock = mock(Authentication.class);

        // Create a single authority "ROLE_USER"
        GrantedAuthority userAuthority = new SimpleGrantedAuthority("ROLE_USER");

        // Set the authorities on the mock
        when(authenticationMock.getAuthorities()).thenAnswer(invocation -> Collections.singletonList(userAuthority));

        // Call the method under test
        boolean hasAdminRole = RoleUtils.hasRoleAdmin(authenticationMock);

        // Verify the result
        assertFalse(hasAdminRole);
    }
}



