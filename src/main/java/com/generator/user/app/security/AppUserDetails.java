package com.generator.user.app.security;

import com.generator.user.app.dto.UserSearchDTO;
import com.generator.user.app.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AppUserDetails implements UserDetails
{

   private static final long serialVersionUID = -119525700066995907L;

   private User user;

   private UserSearchDTO userSearchDTO;
   
   private Boolean enabled;
   
   private List<GrantedAuthority> listAuthorities;
   
   public AppUserDetails(UserSearchDTO byUsername)
   {
      this.user = user;
   }
   
   public AppUserDetails(UserSearchDTO userSearchDTO, List<GrantedAuthority> listAuthorities, Boolean enabled)
   {
      super();
      this.enabled = enabled;
      this.userSearchDTO = userSearchDTO;
      this.listAuthorities = listAuthorities;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities()
   {
      return this.listAuthorities;
   }

   @Override
   public String getPassword()
   {
      return this.getUser().getPassword();
   }

   @Override
   public String getUsername()
   {
      return this.getUser() != null ? this.getUser().getUsername() : null;
   }

   @Override
   public boolean isAccountNonExpired()
   {
      return true;
   }

   @Override
   public boolean isAccountNonLocked()
   {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired()
   {
      return true;
   }

   @Override
   public boolean isEnabled()
   {
      return this.enabled;
   }

   public UserSearchDTO getUserSearchDTO() {
      return userSearchDTO;
   }

   public void setUserSearchDTO(UserSearchDTO userSearchDTO) {
      this.userSearchDTO = userSearchDTO;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }
}
