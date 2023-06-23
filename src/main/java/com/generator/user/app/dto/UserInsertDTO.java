package com.generator.user.app.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class UserInsertDTO implements Serializable
{

   private static final long serialVersionUID = -4_084_564_163_867_412_641L;

   @ApiModelProperty(notes = "First name of the user")
   private String firstName;

   @ApiModelProperty(notes = "Last name of the user")
   private String lastName;

   @ApiModelProperty(notes = "Birth date of the user")
   private Date birthDate;

   @ApiModelProperty(notes = "City of the user")
   private String city;

   @ApiModelProperty(notes = "Country of the user (ISO 2-letter code)")
   private String country;

   @ApiModelProperty(notes = "URL of the user's avatar image")
   private String avatar;

   @ApiModelProperty(notes = "Company of the user")
   private String company;

   @ApiModelProperty(notes = "Job position of the user")
   private String jobPosition;

   @ApiModelProperty(notes = "Mobile number of the user")
   private String mobile;

   @ApiModelProperty(notes = "Username for user login")
   private String username;

   @ApiModelProperty(notes = "Email address of the user")
   private String email;

   @ApiModelProperty(notes = "Password for user login (randomized between 6 and 10 characters)")
   private String password;

   @ApiModelProperty(notes = "Role of the user (admin or user)")
   private String role;

   public UserInsertDTO() {
      super();
   }

   public UserInsertDTO(String firstName, String lastName, Date birthDate, String city, String country, String avatar, String company, String jobPosition, String mobile, String username, String email, String password, String role) {
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
}
