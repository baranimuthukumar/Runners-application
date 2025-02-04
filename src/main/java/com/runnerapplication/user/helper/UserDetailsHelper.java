package com.runnerapplication.user.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.runnerapplication.user.entity.RunnerProfileEntity;
import com.runnerapplication.user.entity.UserDetailsEntity;
import com.runnerapplication.user.model.RunnerProfile;
import com.runnerapplication.user.model.UserDetails;
import com.runnerapplication.user.repository.UserDetailsRepository;

@Component
public class UserDetailsHelper {
	
	@Autowired
	UserDetailsRepository userDetailsRepository;

	public UserDetails convertToDataBase(UserDetailsEntity userDetailsEntity) {
		UserDetails userDetails = new UserDetails();
		userDetails.setUserId(userDetailsEntity.getUserId());
		userDetails.setUserName(userDetailsEntity.getUserName());
		userDetails.setPassword(userDetailsEntity.getPassword());
		return userDetails;
	}
	
	public UserDetailsEntity convertToEntity(UserDetails userDetails) {
		UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
		userDetailsEntity.setUserId(userDetails.getUserId());
		userDetailsEntity.setUserName(userDetails.getUserName());
		userDetailsEntity.setPassword(userDetails.getPassword());
		userDetailsEntity.setProfileId(userDetails.getProfileId());
		return userDetailsEntity;
	}

	public UserDetails convertToDataBaseFromAdm(RunnerProfile runnerProfile) {
		UserDetails userDetails = new UserDetails();
		UserDetails userDetail=userDetailsRepository.getUserId(runnerProfile.getProfileId());
		if(userDetail!=null && userDetail.getUserId()>0) {
			userDetails.setUserId(userDetail.getUserId());
		}
		userDetails.setUserName(runnerProfile.getUserName());
		userDetails.setPassword(runnerProfile.getEmail());
		userDetails.setProfileId(runnerProfile.getProfileId());
		return userDetails;
	}
}
