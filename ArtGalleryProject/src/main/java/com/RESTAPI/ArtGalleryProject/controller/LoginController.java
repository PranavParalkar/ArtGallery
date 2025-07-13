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

	private String emailPattern = "^[a-zA-Z0-9.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9.-]+$";
	private String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";

	// Registration Process
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody LoginCredentials logincred, @RequestParam String confirmPassword) {
		String email = logincred.getEmail();
		String password = logincred.getPassword();
		if (!email.matches(emailPattern)) {
			return new ResponseEntity<>("Invalid email format", HttpStatus.BAD_REQUEST);
		}

		if (!password.matches(passwordPattern)) {
			return new ResponseEntity<>(
					"Password should contain atleast 8 characters, 1 capital letter, 1 small letter, 1 digit, and 1 special character",
					HttpStatus.BAD_REQUEST);
		}
		
		if(!password.equals(confirmPassword)) {
			return new ResponseEntity<>("Passwords don't match", HttpStatus.BAD_REQUEST);
		}

		String response = loginservice.register(logincred);
		switch (response) {
		case "Account already exists":
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			
		case "Registration Successful":
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		default:
			return new ResponseEntity<>("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Login Process
	@PostMapping("/login")
	public ResponseEntity<?> validateLogin(@RequestBody LoginCredentials logincred) {		
		if (!logincred.getEmail().matches(emailPattern)) {
			return new ResponseEntity<>("Invalid email format", HttpStatus.BAD_REQUEST);
		}

		String response = loginservice.validateLogin(logincred);

		switch (response) {
		case "Invalid Email":
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

		case "Invalid Password":
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);

		case "Login Successful":
			return new ResponseEntity<>(response, HttpStatus.OK);

		default:
			return new ResponseEntity<>("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
