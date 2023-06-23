package com.generator.user.app.utils;

import org.springframework.security.core.Authentication;

public class RoleUtils {

    public static boolean hasRoleAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(Object::toString)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }


}
