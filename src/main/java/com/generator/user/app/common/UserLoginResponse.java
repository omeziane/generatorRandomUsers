package com.generator.user.app.common;

import io.swagger.annotations.ApiModelProperty;

public class UserLoginResponse
{

   @ApiModelProperty(value = "Id Field")
   private Long id;
   @ApiModelProperty(notes = "First name of the user")
   private String firstName;

   @ApiModelProperty(notes = "Last name of the user")
   private String lastName;

   @ApiModelProperty(notes = "Username for user login")
   private String username;

   public UserLoginResponse(Long id, String firstName, String lastName, String username) {
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.username = username;
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

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }
}
