package com.RESTAPI.ArtGalleryProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.RESTAPI.ArtGalleryProject.Entity.LoginCredentials;
import com.RESTAPI.ArtGalleryProject.service.LoginRoles;

@RestController
@RequestMapping("/auth/")
public class LoginController {
	
	@Autowired
	private LoginRoles loginservice;
	
	@GetMapping("/home")
	public String home() {
		return "This is home";
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody LoginCredentials logincred) {
		if(loginservice.existsById(logincred.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Account already exists!");
		}
		
		String emailPattern = "^[a-zA-Z0-9.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9.-]+$";
		String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
		
		if(!logincred.getEmail().matches(emailPattern))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Email");
		if(!logincred.getPassword().matches(passwordPattern))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password should contain atleast 8 Characters, a Capital letter, a Small letter, a Digit, and a Special Character");
		
		return ResponseEntity.ok(loginservice.register(logincred));
	}
	
}
