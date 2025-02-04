package com.runnerapplication.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runnerapplication.user.entity.UserDetailsEntity;
import com.runnerapplication.user.helper.UserDetailsHelper;
import com.runnerapplication.user.model.RunnerProfile;
import com.runnerapplication.user.model.UserDetails;
import com.runnerapplication.user.repository.RunnerProfileRepository;
import com.runnerapplication.user.repository.UserDetailsRepository;

@Service
public class LoginService {
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	RunnerProfileRepository runnerProfileRepository;

	@Autowired
	UserDetailsHelper userDetailsHelper;
	
	public UserDetailsEntity checkData(UserDetailsEntity userDetailsEntity) {
		UserDetails userDetails =userDetailsRepository.getUserData(userDetailsEntity.getUserName());
		if(userDetailsEntity.getUserName().equals("Admin")) {
			if(userDetails.getPassword().equals(userDetailsEntity.getPassword())) {
				userDetailsEntity.setValid(true);
				userDetailsEntity.setProfileId(userDetails.getProfileId());
			}else {
				userDetailsEntity.setValid(false);
			}
		}else {
			RunnerProfile runnerProfile=runnerProfileRepository.getById(userDetails.getProfileId());
			if(userDetails.getPassword().equals(userDetailsEntity.getPassword())) {
				userDetailsEntity.setValid(true);
				userDetailsEntity.setProfileId(userDetails.getProfileId());
			}else {
				userDetailsEntity.setValid(false);
			}
		}
		return userDetailsEntity;
	}
	
	public UserDetailsEntity createUserAccount(UserDetailsEntity userDetailsEntity) {
		UserDetails userDetails = userDetailsHelper.convertToDataBase(userDetailsEntity);
		return userDetailsHelper.convertToEntity(userDetailsRepository.save(userDetails));	
	}
}
