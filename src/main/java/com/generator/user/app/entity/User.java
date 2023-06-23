package com.generator.user.app.entity;

import com.generator.user.app.dto.UserGeneratorDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "usr_users")
public class User implements Serializable
{

   private static final long serialVersionUID = 6359109992803201653L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;

   @Column(name = "avatar")
   private String avatar;

   @Column(name = "birthdate")
   private Date birthDate;

   @Column(name = "city")
   private String city;

   @Column(name = "company")
   private String company;

   @Column(name = "country")
   private String country;

   @Column(name = "email")
   private String email;

   @Column(name = "firstname")
   private String firstName;

   @Column(name = "jobposition")
   private String jobPosition;

   @Column(name = "lastname")
   private String lastName;

   @Column(name = "mobile")
   private String mobile;

   @Column(name = "password")
   private String password;

   @Column(name = "role")
   private String role;

   @Column(name = "username")
   private String username;

   public User(Long id, String firstName, String lastName, Date birthDate, String city, String country, String avatar, String company, String jobPosition, String mobile, String username, String email, String password, String role) {
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.birthDate = birthDate;
      this.city = city;
      this.country = country;
      this.avatar = avatar;
      this.company = company;
      this.jobPosition = jobPosition;
      this.mobile = mobile;
      this.username = username;
      this.email = email;
      this.password = password;
      this.role = role;
   }

   public User() {
      super();
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public Date getBirthDate() {
      return birthDate;
   }

   public void setBirthDate(Date birthDate) {
      this.birthDate = birthDate;
   }

   public String getCity() {
      return city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getCountry() {
      return country;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   public String getAvatar() {
      return avatar;
   }

   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }

   public String getCompany() {
      return company;
   }

   public void setCompany(String company) {
      this.company = company;
   }

   public String getJobPosition() {
      return jobPosition;
   }

   public void setJobPosition(String jobPosition) {
      this.jobPosition = jobPosition;
   }

   public String getMobile() {
      return mobile;
   }

   public void setMobile(String mobile) {
      this.mobile = mobile;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getRole() {
      return role;
   }

   public void setRole(String role) {
      this.role = role;
   }

   public static User fromDto(UserGeneratorDTO userDto) {
      return new User(
              userDto.getFirstName(),
              userDto.getLastName(),
              userDto.getBirthDate(),
              userDto.getCity(),
              userDto.getCountry(),
              userDto.getAvatar(),
              userDto.getCompany(),
              userDto.getJobPosition(),
              userDto.getMobile(),
              userDto.getUsername(),
              userDto.getEmail(),
              userDto.getPassword(),
              userDto.getRole()
      );
   }

   public User(String firstName, String lastName, Date birthDate, String city, String country, String avatar, String company, String jobPosition, String mobile, String username, String email, String password, String role) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.birthDate = birthDate;
      this.city = city;
      this.country = country;
      this.avatar = avatar;
      this.company = company;
      this.jobPosition = jobPosition;
      this.mobile = mobile;
      this.username = username;
      this.email = email;
      this.password = password;
      this.role = role;
   }
}
