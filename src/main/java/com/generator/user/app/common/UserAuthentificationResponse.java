package com.generator.user.app.common;

import io.swagger.annotations.ApiModelProperty;

public class UserAuthentificationResponse
{

   @ApiModelProperty(notes = "Access Token")
   private String accessToken;

   public UserAuthentificationResponse(String accessToken) {
      super();
      this.accessToken = accessToken;
   }

   public String getAccessToken() {
      return accessToken;
   }

   public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
   }
}
