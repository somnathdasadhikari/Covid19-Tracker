package com.example.covidtracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.covidtracker.domain.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	//List<User> findByuserName(String userName);
	 @Query(
		        value = "SELECT * FROM user u where u.username = :username AND u.email = :email", 
		        nativeQuery=true
		    )
		    public Optional<User> findByUserNameAndEmailAddress(@Param("username") String username, 
		                                                    @Param("email") String email);
	 
	 @Query(
		        value = "SELECT * FROM user u where u.username = :username", 
		        nativeQuery=true
		    )
		    public Optional<User> findByUserName(@Param("username") String username);
}
