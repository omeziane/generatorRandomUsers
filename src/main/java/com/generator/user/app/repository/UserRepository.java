package com.generator.user.app.repository;

import com.generator.user.app.dto.UserSearchDTO;
import com.generator.user.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{

   @Query("SELECT new com.generator.user.app.dto.UserSearchDTO(u.id, u.firstName, u.lastName, u.birthDate, u.city, u.country, u.avatar, u.company, u.jobPosition, u.mobile, u.username, u.email, u.password, u.role) " +
           "FROM User u " +
           "WHERE u.username = :username")
   UserSearchDTO findByUsername(@Param("username") String username);

   @Query("SELECT new com.generator.user.app.dto.UserSearchDTO(u.id, u.firstName, u.lastName, u.birthDate, u.city, u.country, u.avatar, u.company, u.jobPosition, u.mobile, u.username, u.email, u.password, u.role) " +
           "FROM User u")
   List<UserSearchDTO> findAllEntity();
}
