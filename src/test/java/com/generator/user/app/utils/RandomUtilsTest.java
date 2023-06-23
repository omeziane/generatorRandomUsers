package com.generator.user.app.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class RandomUtilsTest {

    @Test
    void testGenerateRandomFirstName() {
        String firstName = RandomUtils.generateRandomFirstName();
        Assertions.assertNotNull(firstName);
        Assertions.assertNotEquals("", firstName);
    }

    @Test
    void testGenerateRandomLastName() {
        String lastName = RandomUtils.generateRandomLastName();
        Assertions.assertNotNull(lastName);
        Assertions.assertNotEquals("", lastName);
    }

    @Test
    void testGenerateRandomDate() {
        Date date = RandomUtils.generateRandomDate();
        Assertions.assertNotNull(date);
    }

    @Test
    void testGenerateRandomCity() {
        String city = RandomUtils.generateRandomCity();
        Assertions.assertNotNull(city);
        Assertions.assertNotEquals("", city);
    }

    @Test
    void testGenerateRandomCountry() {
        String country = RandomUtils.generateRandomCountry();
        Assertions.assertNotNull(country);
        Assertions.assertNotEquals("", country);
    }

    @Test
    void testGenerateRandomImageUrl() {
        String imageUrl = RandomUtils.generateRandomImageUrl();
        Assertions.assertEquals("https://example.com/avatar.jpg", imageUrl);
    }

    @Test
    void testGenerateRandomJobPosition() {
        String jobPosition = RandomUtils.generateRandomJobPosition();
        Assertions.assertNotNull(jobPosition);
        Assertions.assertNotEquals("", jobPosition);
    }

    @Test
    void testGenerateRandomRole() {
        String role = RandomUtils.generateRandomRole();
        Assertions.assertNotNull(role);
        Assertions.assertNotEquals("", role);
    }

    @Test
    void testGenerateRandomMobile() {
        String mobile = RandomUtils.generateRandomMobile();
        Assertions.assertNotNull(mobile);
        Assertions.assertEquals(10, mobile.length());
    }

    @Test
    void testGenerateRandomEmail() {
        String email = RandomUtils.generateRandomEmail("John", "Doe");
        Assertions.assertNotNull(email);
        Assertions.assertTrue(email.matches("john.doe@.*"));
    }

    @Test
    void testGenerateRandomPassword() {
        String password = RandomUtils.generateRandomPassword();
        Assertions.assertNotNull(password);
        Assertions.assertTrue(password.length() >= 6 && password.length() <= 10);
    }

    @Test
    void testGenerateRandomPhoneNumber() {
        String phoneNumber = RandomUtils.generateRandomPhoneNumber();
        Assertions.assertNotNull(phoneNumber);
        Assertions.assertEquals(10, phoneNumber.length());
    }

    @Test
    void testGenerateRandomCompany() {
        String company = RandomUtils.generateRandomCompany();
        Assertions.assertNotNull(company);
        Assertions.assertNotEquals("", company);
    }

    @Test
    void testGenerateUsername() {
        String username = RandomUtils.generateUsername("John", "Doe");
        Assertions.assertNotNull(username);
        Assertions.assertEquals("jdoe", username);
    }

    @Test
    void testGenerateRandomString() {
        String[] array = {"Value 1", "Value 2", "Value 3"};
        String randomString = RandomUtils.generateRandomString(array);
        Assertions.assertNotNull(randomString);
        Assertions.assertTrue(randomString.equals("Value 1") || randomString.equals("Value 2") || randomString.equals("Value 3"));
    }
}
