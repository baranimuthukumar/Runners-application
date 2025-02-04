package com.runnerapplication.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.runnerapplication.user.model.UserDetails;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long>{
	
	@Query("SELECT usd FROM UserDetails usd WHERE usd.userName = :userName")
	UserDetails getUserData(@Param("userName")String userName);
	
	@Query("SELECT usd FROM UserDetails usd WHERE usd.profileId = :profileId")
	UserDetails getUserId(@Param("profileId")long profileId);
}

