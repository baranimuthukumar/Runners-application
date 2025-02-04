package com.runnerapplication.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.runnerapplication.user.entity.MarathonEventEntity;
import com.runnerapplication.user.entity.MarathonProfileEntity;
import com.runnerapplication.user.entity.RunnerProfileEntity;
import com.runnerapplication.user.services.MarathonProfileService;

@CrossOrigin(origins="*")
@RestController
public class MarathonProfileController {
	
	@Autowired
	MarathonProfileService marathonProfileService;
	
	@PostMapping("/addUserProfile")
	public MarathonProfileEntity saveProfileData(@RequestBody MarathonProfileEntity marathonProfileEntity) {
		return marathonProfileService.saveOrUpdateProfileData(marathonProfileEntity);
	}
	
	@PostMapping("/addRunnerProfile")
	public RunnerProfileEntity saveData(@RequestBody RunnerProfileEntity RunnerProfileEntity) {
		return marathonProfileService.saveOrUpdateRunnerData(RunnerProfileEntity);
	}
	
	@GetMapping("/getRunnerProfile/{profileId}")
	public RunnerProfileEntity getProfileData(@PathVariable("profileId") String profileId) {
		return marathonProfileService.getProfileData(profileId);
	}
	
	@GetMapping("/getAllRunnerData")
	public List<RunnerProfileEntity> getAllRunnerData() {
		return marathonProfileService.getAllRunnerData();
	}
	
	@DeleteMapping("/deleteRunnerData")
	public void deleteRunnerData(@RequestBody RunnerProfileEntity runnerProfileEntity) {
		 marathonProfileService.deleteRunnerData(runnerProfileEntity);
	}
	
	@DeleteMapping("/deleteMarathonData/{registerId}")
	public void deleteMarathonData(@PathVariable("registerId") String registerId) {
		 marathonProfileService.deleteMarathonData(registerId);
	}
	
	@GetMapping("/getAllRunnerFutureData")
	public List<RunnerProfileEntity> getAllRunnerFutureData(@RequestBody MarathonProfileEntity marathonProfileEntity) {
		return marathonProfileService.getAllRunnerFutureData(marathonProfileEntity);
	}
	
	@GetMapping("/getAllActiveMemberData/{activeMember}")
	public List<RunnerProfileEntity> getAllActiveMemberData(@PathVariable("activeMember") boolean activeMember) {
		return marathonProfileService.getAllActiveMemberData(activeMember);
	}
	
	@GetMapping("/getAllFutureMarathonData")
	public List<MarathonEventEntity> getAllFutureMarathonData() {
		return marathonProfileService.getAllFutureMarathonData();
	}
	
	
	
}
