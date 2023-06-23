package com.generator.user.app.controller;

import com.generator.user.app.common.UserAuthentificationResponse;
import com.generator.user.app.common.UserLoginRequest;
import com.generator.user.app.common.UserLoginResponse;
import com.generator.user.app.security.AppUserDetails;
import com.generator.user.app.security.AppUserDetailsService;
import com.generator.user.app.security.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Api(tags = "User Authentification")
public class UserLoginController
{

   @Autowired
   private AppUserDetailsService userDetailsService;

   @Autowired
   private AuthenticationManager authenticationManager;

   @Autowired
   private JwtTokenUtil jwtTokenUtil;

   @ApiOperation(value = "Login")
   @ApiResponses(value = { 
      @ApiResponse(code = 200, message = "Successful operation", response = UserLoginResponse.class),
      @ApiResponse(code = 204, message = "No records found"), 
      @ApiResponse(code = 401, message = "Access denied"),
      @ApiResponse(code = 403, message = "You doesn't have permission"), 
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 500, message = "Internal server error") 
   })
   @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Object> login(@RequestBody UserLoginRequest request)
   {
      HttpHeaders responseHeaders = new HttpHeaders();
      UserAuthentificationResponse response = null;
      try
      {
         validateRequest(request);
         authenticate(request.getUsername(), request.getPassword());
         
         final AppUserDetails userDetails = (AppUserDetails) userDetailsService.loadUserByUsername(request.getUsername());
         final String token = jwtTokenUtil.generateToken(userDetails);
         
         responseHeaders.set("Authorization", "Bearer " + token);

         if (userDetails == null || userDetails.getUser() == null)
         {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Bad credentials");
         }

         response = new UserAuthentificationResponse(token);
      }
      catch (Exception e)
      {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
      }

      return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(response);
   }

   private void validateRequest(UserLoginRequest request) throws Exception
   {
      if (request.getUsername() == null || "".equals(request.getUsername().trim()))
      {
         throw new Exception("Username is required");
      }
      if (request.getUsername() == null || "".equals(request.getPassword().trim()))
      {
         throw new Exception("Password is required");
      }
   }

   private void authenticate(String username, String password) throws Exception
   {
      try
      {
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      }
      catch (BadCredentialsException e)
      {
         throw new BadCredentialsException("Bad credentials");
      }
   }

}
