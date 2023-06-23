package com.generator.user.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{

   private static final String[] whitelist = new String[] { 
      "/v2/api-docs", 
      "/configuration/ui", 
      "/swagger-resources/**",
      "/configuration/security", 
      "/swagger-ui.html", 
      "/webjars/**" ,
      "/login",
      "/h2-console/**",
      "/api/users/**",
      "/api/auth/**"
   };

   @Autowired
   private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

   @Autowired
   private UserDetailsService userDetailsService;

   @Autowired
   private JwtRequestFilter jwtRequestFilter;

   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
   {
      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
   }

   @Bean
   public PasswordEncoder passwordEncoder()
   {
      return new PasswordEncoder()
      {

         @Override
         public boolean matches(CharSequence rawPassword, String encodedPassword)
         {
            if (rawPassword == null)
            {
               return false;
            }
            
            return md5(rawPassword.toString()).equals(encodedPassword);
         }

         @Override
         public String encode(CharSequence rawPassword)
         {
            return md5(rawPassword.toString());
         }
      };
   }

   @Bean
   @Override
   public AuthenticationManager authenticationManagerBean() throws Exception
   {
      return super.authenticationManagerBean();
   }

   @Override
   protected void configure(HttpSecurity httpSecurity) throws Exception
   {

      httpSecurity.csrf().disable().authorizeRequests()
              .antMatchers(whitelist).permitAll().anyRequest()
            .authenticated().and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
      httpSecurity.headers().frameOptions().disable();
      httpSecurity.headers().frameOptions().sameOrigin();
   }
   
   private String md5(String text)
   {
      MessageDigest m = null;

      try
      {
         m = MessageDigest.getInstance("MD5");

         m.update(text.getBytes(), 0, text.length());
      }
      catch (NoSuchAlgorithmException e)
      {
         e.printStackTrace();
      }

      return new BigInteger(1, m.digest()).toString(16);
   }
}
