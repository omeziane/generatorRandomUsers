package com.generator.user.app.service;

import com.generator.user.app.dto.UserGeneratorDTO;
import com.generator.user.app.dto.UserInsertDTO;
import com.generator.user.app.dto.UserSearchDTO;
import com.generator.user.app.dto.UserUpdateDTO;

import java.util.List;

public interface UserService
{

   public List<UserSearchDTO> findAllEntity();
   
   public void insert(UserInsertDTO dto);
   
   public void update(UserUpdateDTO dto);
   
   public void delete(Long id);

   public List<UserGeneratorDTO> generateRandomUsers(int count);

   UserSearchDTO getUserProfile(String username);
}
