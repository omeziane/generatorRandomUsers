package com.generator.user.app.service;

import com.generator.user.app.dto.UserGeneratorDTO;
import com.generator.user.app.dto.UserInsertDTO;
import com.generator.user.app.dto.UserSearchDTO;
import com.generator.user.app.dto.UserUpdateDTO;
import com.generator.user.app.entity.User;
import com.generator.user.app.repository.UserRepository;
import com.generator.user.app.utils.PasswordUtils;
import com.generator.user.app.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService
{

   @Autowired
   UserRepository repository;
   
   @Override
   public List<UserSearchDTO> findAllEntity()
   {
      return repository.findAllEntity();
   }

   @Override
   public void insert(UserInsertDTO userDTO)
   {
      User user = new User();
      user.setFirstName(userDTO.getFirstName());
      user.setLastName(userDTO.getLastName());
      user.setBirthDate(userDTO.getBirthDate());
      user.setCity(userDTO.getCity());
      user.setCountry(userDTO.getCountry());
      user.setAvatar(userDTO.getAvatar());
      user.setCompany(userDTO.getCompany());
      user.setJobPosition(userDTO.getJobPosition());
      user.setMobile(userDTO.getMobile());
      user.setUsername(userDTO.getUsername());
      user.setEmail(userDTO.getEmail());
      user.setPassword(PasswordUtils.hashPasswordMD5(userDTO.getPassword()));
      user.setRole(userDTO.getRole());
      this.repository.save(user);
   }

   @Override
   public void update(UserUpdateDTO userDTO)
   {
      Optional<User> optionalEntity = this.repository.findById(userDTO.getId());
      
      if (optionalEntity.isPresent())
      {
         User entity = optionalEntity.get();
         entity.setId(userDTO.getId());
         User user = new User();
         user.setFirstName(userDTO.getFirstName());
         user.setLastName(userDTO.getLastName());
         user.setBirthDate(userDTO.getBirthDate());
         user.setCity(userDTO.getCity());
         user.setCountry(userDTO.getCountry());
         user.setAvatar(userDTO.getAvatar());
         user.setCompany(userDTO.getCompany());
         user.setJobPosition(userDTO.getJobPosition());
         user.setMobile(userDTO.getMobile());
         user.setUsername(userDTO.getUsername());
         user.setEmail(userDTO.getEmail());
         user.setPassword(PasswordUtils.hashPasswordMD5(userDTO.getPassword()));
         user.setRole(userDTO.getRole());
         this.repository.save(entity);
      }
   }

   @Override
   public void delete(Long id)
   {
      this.repository.deleteById(id);
   }

   @Override
   public List<UserGeneratorDTO> generateRandomUsers(int count) {
      List<UserGeneratorDTO> users = new ArrayList<>();

      for (int i = 0; i < count; i++) {
         UserGeneratorDTO user = generateRandomUser();
         user.setFirstName(RandomUtils.generateRandomFirstName());
         user.setLastName(RandomUtils.generateRandomLastName());
         user.setBirthDate(RandomUtils.generateRandomDate());
         user.setCity(RandomUtils.generateRandomCity());
         user.setCountry(RandomUtils.generateRandomCountry());
         user.setAvatar(RandomUtils.generateRandomImageUrl());
         user.setCompany(RandomUtils.generateRandomCompany());
         user.setJobPosition(RandomUtils.generateRandomJobPosition());
         user.setMobile(RandomUtils.generateRandomPhoneNumber());
         user.setUsername(RandomUtils.generateUsername(user.getFirstName(), user.getLastName()));
         user.setEmail(RandomUtils.generateRandomEmail(user.getFirstName(), user.getLastName()));
         user.setPassword(RandomUtils.generateRandomPassword());
         user.setRole(RandomUtils.generateRandomRole());

         users.add(user);
      }

      return users;
   }

   @Override
   public UserSearchDTO getUserProfile(String username) {
      return this.repository.findByUsername(username);
   }

   private UserGeneratorDTO generateRandomUser() {
      Random random = new Random();

      String[] firstNames = { "John", "Jane", "Michael", "Emma" };
      String[] lastNames = { "Doe", "Smith", "Johnson", "Brown" };
      String[] cities = { "Berlin", "Paris", "London", "New York" };
      String[] countries = { "DE", "FR", "UK", "US" };
      String[] avatars = { "https://example.com/avatar1.jpg", "https://example.com/avatar2.jpg", "https://example.com/avatar3.jpg" };
      String[] companies = { "Acme Inc", "XYZ Corporation", "ABC Company" };
      String[] jobPositions = { "Software Engineer", "Product Manager", "Sales Representative" };
      String[] roles = { "admin", "user" };

      String firstName = getRandomElement(firstNames);
      String lastName = getRandomElement(lastNames);
      Date birthDate = generateRandomDate();
      String city = getRandomElement(cities);
      String country = getRandomElement(countries);
      String avatar = getRandomElement(avatars);
      String company = getRandomElement(companies);
      String jobPosition = getRandomElement(jobPositions);
      String mobile = generateRandomPhoneNumber();
      String username = generateRandomUsername(firstName, lastName);
      String email = generateRandomEmail(firstName, lastName);
      String password = generateRandomPassword(6, 10);
      String role = getRandomElement(roles);

      UserGeneratorDTO user = new UserGeneratorDTO();
      user.setFirstName(firstName);
      user.setLastName(lastName);
      user.setBirthDate(birthDate);
      user.setCity(city);
      user.setCountry(country);
      user.setAvatar(avatar);
      user.setCompany(company);
      user.setJobPosition(jobPosition);
      user.setMobile(mobile);
      user.setUsername(username);
      user.setEmail(email);
      user.setPassword(password);
      user.setRole(role);

      return user;
   }

   private <T> T getRandomElement(T[] array) {
      Random random = new Random();
      int index = random.nextInt(array.length);
      return array[index];
   }

   private Date generateRandomDate() {
      String startDate = "1970-01-01";
      String endDate = "2005-12-31";
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      try {
         Date start = format.parse(startDate);
         Date end = format.parse(endDate);
         long timestamp = getRandomInRange(start.getTime(), end.getTime());
         return new Date(timestamp);
      } catch (ParseException e) {
         e.printStackTrace();
         return null;
      }
   }

   private long getRandomInRange(long min, long max) {
      Random random = new Random();
      return min + (long) (random.nextDouble() * (max - min));
   }

   private String generateRandomPhoneNumber() {
      Random random = new Random();
      StringBuilder phoneNumber = new StringBuilder();

      // Generate random digits for the phone number
      for (int i = 0; i < 10; i++) {
         int digit = random.nextInt(10);
         phoneNumber.append(digit);
      }

      return phoneNumber.toString();
   }

   private String generateRandomUsername(String firstName, String lastName) {
      Random random = new Random();
      int randomNumber = random.nextInt(10000);
      return firstName.toLowerCase() + lastName.toLowerCase() + randomNumber;
   }

   private String generateRandomEmail(String firstName, String lastName) {
      String[] domains = { "gmail.com", "yahoo.com", "hotmail.com" };
      String domain = getRandomElement(domains);
      return firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + domain;
   }

   private String generateRandomPassword(int minLength, int maxLength) {
      Random random = new Random();
      String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
      int length = random.nextInt(maxLength - minLength + 1) + minLength;
      StringBuilder password = new StringBuilder();

      // Generate random characters for the password
      for (int i = 0; i < length; i++) {
         int index = random.nextInt(characters.length());
         char character = characters.charAt(index);
         password.append(character);
      }

      return password.toString();
   }

}
