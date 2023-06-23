package com.generator.user.app.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.generator.user.app.common.UploadUsersResponse;
import com.generator.user.app.dto.UserGeneratorDTO;
import com.generator.user.app.dto.UserInsertDTO;
import com.generator.user.app.dto.UserSearchDTO;
import com.generator.user.app.dto.UserUpdateDTO;
import com.generator.user.app.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Api(tags = "User Management")
public class UserGeneratorController
{

   @Autowired
   UserService service;
   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
   private int recordsFailed = 0;
   private boolean exceptionCaught = false;
   private String errorMessage = "";

   @ApiOperation(value = "Generate Users", notes = "Generates a JSON file containing a specified number of users.")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Successful operation"),
           @ApiResponse(code = 204, message = "No records found"),
           @ApiResponse(code = 401, message = "Access denied"),
           @ApiResponse(code = 403, message = "You doesn't have permission"),
           @ApiResponse(code = 404, message = "Not found"),
           @ApiResponse(code = 500, message = "Internal server error")
   })
   @GetMapping(value = "generate", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<List<UserGeneratorDTO>> generateUsers(@RequestParam("count") int count) {
      List<UserGeneratorDTO> users = service.generateRandomUsers(count);

      HttpHeaders headers = new HttpHeaders();
      headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.json");

      return ResponseEntity.ok()
              .headers(headers)
              .body(users);
   }

   @ApiOperation(value = "Upload Users File and Create Users in Database")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Successful operation"),
           @ApiResponse(code = 204, message = "No records found"),
           @ApiResponse(code = 401, message = "Access denied"),
           @ApiResponse(code = 403, message = "You doesn't have permission"),
           @ApiResponse(code = 404, message = "Not found"),
           @ApiResponse(code = 500, message = "Internal server error")
   })
   @PostMapping(value = "batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<Object> uploadUsersFile(@RequestParam("file") MultipartFile file) {
      try {
         // Logic to process the uploaded file and store the users in the database
         List<UserInsertDTO> users = processUploadedFile(file);
         int totalRecords = users.size();
         int recordsImported = 0;

         // Iterate through the users and attempt to save them in the database
         for (UserInsertDTO user : users) {
            try {
               service.insert(user);
               recordsImported++;
            } catch (Exception e) {
               handleInsertException(e);
            }
         }

         UploadUsersResponse response = new UploadUsersResponse();
         // Set the response data
         response.setTotalRecords(totalRecords);
         response.setRecordsImported(recordsImported);
         response.setRecordsFailed(recordsFailed);
         if (!exceptionCaught) {
            // Exception was not caught, set the message here
            String message = "No exception occurred";
            response.setMessage(message);
         }
         else {
            response.setMessage(errorMessage);
         }

         // Return the response with appropriate status code
         return ResponseEntity.status(HttpStatus.OK).body(response);
      } catch (IOException e) {
         // Failed to read the file or other IO error occurred
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
      } catch (Exception e) {
         // Other exceptions occurred
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON file.");
      }
   }

   private void handleInsertException(Exception e) {
      recordsFailed++;
      exceptionCaught = true;
      errorMessage = e.getMessage();
   }

   @ApiOperation(value = "Search")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Successful operation", response = UserGeneratorDTO.class, reference = "List"),
           @ApiResponse(code = 204, message = "No records found"),
           @ApiResponse(code = 401, message = "Access denied"),
           @ApiResponse(code = 403, message = "You doesn't have permission"),
           @ApiResponse(code = 404, message = "Not found"),
           @ApiResponse(code = 500, message = "Internal server error")
   })
   @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
   public List<UserSearchDTO> get(@RequestHeader(name = "Authorization", required = false) String token)
   {
      return service.findAllEntity();
   }

   @ApiOperation(value = "Insert")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Successful operation"),
           @ApiResponse(code = 204, message = "No records found"),
           @ApiResponse(code = 401, message = "Access denied"),
           @ApiResponse(code = 403, message = "You doesn't have permission"),
           @ApiResponse(code = 404, message = "Not found"),
           @ApiResponse(code = 500, message = "Internal server error")
   })
   @PostMapping
   public void insert(@RequestHeader(name = "Authorization", required = false) String token,
                      @ApiParam(name = "User DTO", value = "Object to insert", required = true) @RequestBody UserInsertDTO dto)
   {
      service.insert(dto);
   }

   @ApiOperation(value = "Update")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Successful operation"),
           @ApiResponse(code = 204, message = "No records found"),
           @ApiResponse(code = 401, message = "Access denied"),
           @ApiResponse(code = 403, message = "You doesn't have permission"),
           @ApiResponse(code = 404, message = "Not found"),
           @ApiResponse(code = 500, message = "Internal server error")
   })
   @PutMapping
   public void update(@RequestHeader(name = "Authorization", required = false) String token, @ApiParam(name = "User DTO", value = "Object to update", required = true) @RequestBody UserUpdateDTO dto)
   {
      service.update(dto);
   }

   @ApiOperation(value = "Delete")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Successful operation"),
           @ApiResponse(code = 204, message = "No records found"),
           @ApiResponse(code = 401, message = "Access denied"),
           @ApiResponse(code = 403, message = "You doesn't have permission"),
           @ApiResponse(code = 404, message = "Not found"),
           @ApiResponse(code = 500, message = "Internal server error")
   })
   @DeleteMapping
   public void delete(@RequestHeader(name = "Authorization", required = false) String token, @ApiParam(name = "id", value = "User ID", required = true) @RequestParam Long id)
   {
      service.delete(id);
   }

   @ApiOperation(value = "Check Authentification")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Successful operation"),
           @ApiResponse(code = 204, message = "No records found"),
           @ApiResponse(code = 401, message = "Access denied"),
           @ApiResponse(code = 403, message = "You doesn't have permission"),
           @ApiResponse(code = 404, message = "Not found"),
           @ApiResponse(code = 500, message = "Internal server error")
   })
   @GetMapping("me")
   @PreAuthorize("isAuthenticated()")
   public ResponseEntity<UserSearchDTO> getMyProfile() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String username = authentication.getName();

      // Use the username to fetch the user's profile information from the database or any other data source
      UserSearchDTO userProfile = service.getUserProfile(username);

      return ResponseEntity.ok(userProfile);
   }

   @ApiOperation("Get user profile")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Successful operation"),
           @ApiResponse(code = 204, message = "No records found"),
           @ApiResponse(code = 401, message = "Access denied"),
           @ApiResponse(code = 403, message = "You doesn't have permission"),
           @ApiResponse(code = 404, message = "Not found"),
           @ApiResponse(code = 500, message = "Internal server error")
   })
   @GetMapping("{username}")
   @PreAuthorize("isAuthenticated() or hasRole('admin') ")
   public ResponseEntity<UserSearchDTO> getUserProfile(@PathVariable String username) {
      UserSearchDTO userProfile;
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      UserSearchDTO userProfileFromToken = service.getUserProfile(authentication.getName());
      if (userProfileFromToken.getRole().equals("admin")) {
         // Fetch the user's profile information from the database or any other data source
         userProfile = service.getUserProfile(username);
      } else {
         UserDetails userDetails = (UserDetails) authentication.getPrincipal();
         String authenticatedUsername = userDetails.getUsername();

         if (username.equals(authenticatedUsername)) {
            // Fetch the user's profile information from the database or any other data source
            userProfile = service.getUserProfile(username);
         } else {
            // Return an error response indicating unauthorized access
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
         }
      }
      return ResponseEntity.status(HttpStatus.OK).body(userProfile);
   }


   public List<UserInsertDTO> processUploadedFile(MultipartFile file) throws IOException {

      List<UserInsertDTO> users = new ArrayList<>();

      try {
         ObjectMapper objectMapper = new ObjectMapper();
         JsonNode rootNode = objectMapper.readTree(file.getInputStream());
         if (rootNode.isArray()) {
            ArrayNode usersArray = (ArrayNode) rootNode;
            for (JsonNode userNode : usersArray) {

               UserInsertDTO user = new UserInsertDTO();
               // Set the user attributes based on the JSON data
               user.setFirstName(userNode.get("firstName").asText());
               user.setLastName(userNode.get("lastName").asText());
               // Convert birthDate string to Date
               Date birthDate = null;
               try {
                  birthDate = formatter.parse(userNode.get("birthDate").asText());
               } catch (ParseException e) {
                  throw new RuntimeException("Error inserting date" + e.getMessage());
               }
               ;
               user.setBirthDate(birthDate);
               user.setCity(userNode.get("city").asText());
               user.setCountry(userNode.get("country").asText());
               user.setAvatar(userNode.get("avatar").asText());
               user.setCompany(userNode.get("company").asText());
               user.setJobPosition(userNode.get("jobPosition").asText());
               user.setMobile(userNode.get("mobile").asText());
               user.setUsername(userNode.get("username").asText());
               user.setEmail(userNode.get("email").asText());
               user.setPassword(userNode.get("password").asText()); // Assuming password is stored as a string in the JSON
               user.setRole(userNode.get("role").asText());
               users.add(user);
            }
         }
      } catch (IOException e) {
         throw new IOException("Error processing the uploaded file : " + e);
      }
      return users;
   }

}
