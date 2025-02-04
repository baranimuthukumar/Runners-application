package com.runnerapplication.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.runnerapplication.user.entity.MarathonEventEntity;
import com.runnerapplication.user.entity.TopperEntitity;
import com.runnerapplication.user.entity.YearDataEntitity;
import com.runnerapplication.user.services.AdminService;

@CrossOrigin(origins="*")
@RestController
public class AdminController {
	
	
	@Autowired
	AdminService adminService;
	
	@PostMapping("/addEventName")
	public MarathonEventEntity saveData(@RequestBody MarathonEventEntity marathonEventEntity) {
		return adminService.saveData(marathonEventEntity);
	}
	
	@GetMapping("/getTopperData")
	public List<TopperEntitity> getTopperDetails() {
		return adminService.getTopperDetails();
	}
	
	@PostMapping("/addWholeYearData")
	public boolean addWholeYearData(@RequestBody YearDataEntitity yearDataEntitity) throws JsonProcessingException {
		adminService.addWholeYearData(yearDataEntitity);
		return true;
	}
	
	@GetMapping("/getAllAcesToken")
	public List<TopperEntitity> getAllAcesToken() {
		return adminService.getAllAcesToken();
	}
	
	@PostMapping("/replaceBymonth")
	public List<TopperEntitity> getReplaceTopperDetails(@RequestBody List<TopperEntitity> topperEntitityList) {
		return adminService.sortTopperDetils(topperEntitityList);
	}
}
