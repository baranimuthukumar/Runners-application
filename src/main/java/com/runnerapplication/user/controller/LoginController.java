package com.runnerapplication.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.runnerapplication.user.entity.UserDetailsEntity;
import com.runnerapplication.user.services.LoginService;

@CrossOrigin(origins="*")
@RestController
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	@PostMapping("/checkLoginDetails")
	public UserDetailsEntity saveData(@RequestBody UserDetailsEntity userDetailsEntity) {
		return loginService.checkData(userDetailsEntity);
	}
	
	@PostMapping("/createUserAccount")
	public UserDetailsEntity addUserAccount(@RequestBody UserDetailsEntity userDetailsEntity) {
		return loginService.createUserAccount(userDetailsEntity);
	}
}
