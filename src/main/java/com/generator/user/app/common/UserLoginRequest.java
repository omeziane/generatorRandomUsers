package com.generator.user.app.common;

import io.swagger.annotations.ApiModelProperty;

public class UserLoginRequest
{

   @ApiModelProperty(value = "User login")
   private String username;
   
   @ApiModelProperty(value = "User password")
   private String password;
   
   /**
    * 
    */
   public UserLoginRequest()
   {
      super();
   }

   /**
    * @param username
    * @param password
    */
   public UserLoginRequest(String username, String password)
   {
      super();
      this.username = username;
      this.password = password;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }
}
