package com.generator.user.app.utils;

import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PasswordUtilsTest {

    @Test
    void testHashPasswordMD5() {
        String password = "password";
        String hashedPassword = PasswordUtils.hashPasswordMD5(password);

        assertNotNull(hashedPassword);
        assertEquals(32, hashedPassword.length());

        // You can also validate the hashed password using another MD5 implementation
        String expectedHashedPassword = calculateExpectedMD5Hash(password);
        assertEquals(expectedHashedPassword, hashedPassword);
    }

    private String calculateExpectedMD5Hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
