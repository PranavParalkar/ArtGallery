package com.RESTAPI.ArtGalleryProject.controller.LoginANDsignup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.RESTAPI.ArtGalleryProject.DTO.LoginANDsignup.LoginRequest;
import com.RESTAPI.ArtGalleryProject.DTO.LoginANDsignup.SignupRequest;
import com.RESTAPI.ArtGalleryProject.DTO.LoginANDsignup.UserDetailRequest;
import com.RESTAPI.ArtGalleryProject.service.loginANDsignup.LoginService;

@RestController
@RequestMapping("/auth/")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService service;

	private String emailPattern = "^[a-zA-Z0-9.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9.-]+$";
	private String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
	private String phonePattern = "^[6-9]\\d{9}$";
	private String pinCodePattern = "^[1-9][0-9]{5}$";

	// Registration Process
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest request) {
		logger.info("registerUser started.");
		String email = request.email();
		String password = request.password();
		String confirmPassword = request.confirmPassword();
		if (!email.matches(emailPattern)) {
			logger.info("registerUser finished.");
			return new ResponseEntity<>("Invalid email format", HttpStatus.BAD_REQUEST);
		}

		if (!password.matches(passwordPattern)) {
			logger.info("registerUser finished.");
			return new ResponseEntity<>(
					"Password should contain atleast 8 characters, 1 capital letter, 1 small letter, 1 digit, and 1 special character",
					HttpStatus.BAD_REQUEST);
		}

		if (!password.equals(confirmPassword)) {
			logger.info("registerUser finished.");
			return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
		}

		String response = service.register(request);
		logger.info("registerUser finished.");
		switch (response) {
		case "Account already exists":
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);

		case "Registration Successful":
			return new ResponseEntity<>(response, HttpStatus.CREATED);

		default:
			return new ResponseEntity<>("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Saving User Info Process
	@PostMapping("/user-info")
	public ResponseEntity<?> saveUserLogin(@Validated @RequestBody UserDetailRequest request) {
		logger.info("saveUserLogin started.");
		if (!request.phoneNumber().matches(phonePattern)) {
			logger.info("saveUserLogin finished.");
			return new ResponseEntity<>("Invalid phone number", HttpStatus.BAD_REQUEST);
		}

		if (!request.address().getPincode().matches(pinCodePattern)) {
			logger.info("saveUserLogin finished.");
			return new ResponseEntity<>("Invalid pincode", HttpStatus.BAD_REQUEST);
		}

		String response = service.acceptDetails(request);
		logger.info("saveUserLogin finished.");
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
	public ResponseEntity<?> validateLogin(@Validated @RequestBody LoginRequest request) {
		logger.info("validateLogin started.");
		if (!request.email().matches(emailPattern)) {
			logger.info("validateLogin finished.");
			return new ResponseEntity<>("Invalid email format", HttpStatus.BAD_REQUEST);
		}

		Object response = service.validateLogin(request);
	    logger.info("validateLogin finished.");

	    if (response instanceof String) {
	        return switch ((String) response) {
	            case "Invalid Email" -> new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	            case "Invalid Password" -> new ResponseEntity<>(response, HttpStatus.CONFLICT);
	            default -> new ResponseEntity<>("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
	        };
	    }

	    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

	}

}
