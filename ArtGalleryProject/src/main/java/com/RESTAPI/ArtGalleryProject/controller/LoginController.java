package com.RESTAPI.ArtGalleryProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.RESTAPI.ArtGalleryProject.Entity.LoginCredentials;
import com.RESTAPI.ArtGalleryProject.service.LoginCredentialsRoles;

@RestController
@RequestMapping("/auth")
public class LoginController {
	
	@Autowired
	private LoginCredentialsRoles loginService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerAccount(@RequestBody LoginCredentials logincred) {
		if(loginService.existsById(logincred.getUsername())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Account already exists!");
		}
		
		loginService.registerAccountLoginCredentials(logincred);
		return ResponseEntity.ok("Registration successful");
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginAccount(@RequestBody LoginCredentials logincred) {
		String response = loginService.validateLogin(logincred);
		switch(response) {
		case "Invalid Username": 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		case "Invalid Password": 
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		default:
			return ResponseEntity.ok(response);
		}
	}
	
}
