package com.generator.user.app.security;

import com.generator.user.app.dto.UserSearchDTO;
import com.generator.user.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService
{
   
   private final UserRepository repository;
   
   @Autowired
   public AppUserDetailsService(UserRepository repository)
   {
      this.repository = repository;
   }

   @Override
   public UserDetails loadUserByUsername(String username)
   {
      UserSearchDTO entity = repository.findByUsername(username);

      if (entity == null)
      {
         throw new UsernameNotFoundException(username);
      }
      
      return new AppUserDetails(entity, null, true);
   }

}
