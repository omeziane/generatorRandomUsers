package com.generator.user.app.utils;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    private RandomUtils() {
        throw new IllegalStateException("Random utility class");
    }
    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 10;
    private static final String[] FIRST_NAMES = {"John", "Alice", "Michael", "Emily", "Daniel", "Olivia", "David", "Sophia", "Matthew", "Emma"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Brown", "Davis", "Wilson", "Miller", "Taylor", "Anderson", "Thomas", "Clark", "Doe", "Williams"};
    private static final String[] CITIES = {"Berlin", "London", "Paris", "New York", "Tanger"};
    private static final String[] COUNTRIES = {"DE", "GB", "FR", "US", "MA"};
    private static final String[] JOB_POSITIONS = {"Software Engineer", "Product Manager", "Data Analyst", "Team Leader", "CEO"};
    private static final String[] ROLES = {"admin", "user"};

    private static final String LINL_AVATAR_JPG = "https://example.com/avatar.jpg";

    private static final Random random = new Random();

    public static String generateRandomFirstName() {
        return generateRandomString(FIRST_NAMES);
    }

    public static String generateRandomLastName() {
        return generateRandomString(LAST_NAMES);
    }

    public static Date generateRandomDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, ThreadLocalRandom.current().nextInt(1900, 2023));
        calendar.set(Calendar.MONTH, ThreadLocalRandom.current().nextInt(0, 12));
        calendar.set(Calendar.DAY_OF_MONTH, ThreadLocalRandom.current().nextInt(1, 29));

        return calendar.getTime();
    }

    public static String generateRandomCity() {
        return generateRandomString(CITIES);
    }

    public static String generateRandomCountry() {
        return generateRandomString(COUNTRIES);
    }

    public static String generateRandomImageUrl() {
        return LINL_AVATAR_JPG;
    }

    public static String generateRandomJobPosition() {
        return generateRandomString(JOB_POSITIONS);
    }

    public static String generateRandomRole() {
        return generateRandomString(ROLES);
    }

    public static String generateRandomMobile() {
        // Generate a random 10-digit number
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }

    public static String generateRandomEmail(String firstName, String lastName) {
        String[] domains = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com"};
        String randomDomain = generateRandomString(domains);
        return firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + randomDomain;
    }

    public static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        int length = getRandomLength();
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            password.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }
        return password.toString();
    }

    private static int getRandomLength() {
        SecureRandom random = new SecureRandom();
        return MIN_PASSWORD_LENGTH + random.nextInt(MAX_PASSWORD_LENGTH - MIN_PASSWORD_LENGTH + 1);
    }

    public static String generateRandomPhoneNumber() {
        StringBuilder phoneNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            phoneNumber.append(random.nextInt(10));
        }
        return phoneNumber.toString();
    }

    public static String generateRandomCompany() {
        String[] companies = {
                "Acme Inc", "Widgets Co", "Tech Solutions", "Global Corp", "Innovative Industries", "ABC Ltd", "YXZ Tanger Med"
        };
        return generateRandomString(companies);
    }

    public static String generateUsername(String firstName, String lastName) {
        String username = firstName.substring(0, 1).toLowerCase() + lastName.toLowerCase();
        return username.replaceAll("\\s+", ""); // Remove any whitespace
    }

    public static String generateRandomString(String[] array) {
        int index = random.nextInt(array.length);
        return array[index];
    }
}
