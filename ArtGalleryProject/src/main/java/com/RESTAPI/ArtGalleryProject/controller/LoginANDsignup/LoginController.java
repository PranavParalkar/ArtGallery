package com.RESTAPI.ArtGalleryProject.controller.LoginANDsignup;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.RESTAPI.ArtGalleryProject.Entity.LoginCredentials;
import com.RESTAPI.ArtGalleryProject.dto.SignupRequest;
import com.RESTAPI.ArtGalleryProject.dto.UserDetailRequest;
import com.RESTAPI.ArtGalleryProject.service.loginANDsignup.LoginRoles;

@RestController
@RequestMapping("/auth/")
public class LoginController {

	@Autowired
	private LoginRoles service;

	private String emailPattern = "^[a-zA-Z0-9.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9.-]+$";
	private String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
	private String phonePattern = "^[6-9]\\d{9}$";
	private String pinCodePattern = "^[1-9][0-9]{5}$";


	// Registration Process
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupcred) {
		String email = signupcred.getEmail();
		String password = signupcred.getPassword();
		String confirmPassword = signupcred.getConfirmPassword();
		if (!email.matches(emailPattern)) {
			return new ResponseEntity<>("Invalid email format", HttpStatus.BAD_REQUEST);
		}

		if (!password.matches(passwordPattern)) {
			return new ResponseEntity<>(
					"Password should contain atleast 8 characters, 1 capital letter, 1 small letter, 1 digit, and 1 special character",
					HttpStatus.BAD_REQUEST);
		}
		
		if(!password.equals(confirmPassword)) {
			return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
		}
		
		LoginCredentials logincred = new LoginCredentials();
		logincred.setEmail(email);
		logincred.setPassword(password);
		logincred.setSecurityQuestion(signupcred.getSecurityQuestion());
		logincred.setSecurityAnswer(signupcred.getSecurityAnswer());
		
		String response = service.register(logincred);
		switch (response) {
		case "Account already exists":
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			
		case "Registration Successful":
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		default:
			return new ResponseEntity<>("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Saving User Info Process
	@PostMapping("/user-info")
	public ResponseEntity<?> saveUserLogin(@RequestBody UserDetailRequest request) {
		if (!request.getPhoneNumber().matches(phonePattern)) {
			return new ResponseEntity<>("Invalid phone number", HttpStatus.BAD_REQUEST);
		}
		
		if (!request.getAddress().getPincode().matches(pinCodePattern)) {
			return new ResponseEntity<>("Invalid pincode", HttpStatus.BAD_REQUEST);
		} 
		
		String response = service.acceptDetails(request);
		switch (response) {
		case "User not Found":
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			
		case "User info saved":
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

		String response = service.validateLogin(logincred);

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
