package com.RESTAPI.ArtGalleryProject.controller.DashBoard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.RESTAPI.ArtGalleryProject.security.AuthHelper;
import com.RESTAPI.ArtGalleryProject.service.DashBoard.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private AuthHelper authHelper;
    
	@Autowired
	private UserService service;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
    	logger.info("getUserProfile started.");
        long userId = authHelper.getCurrentUserId();
        String email = authHelper.getCurrentEmail();
    	Object response = service.getUserDetials(userId, email);
    	if(response instanceof String) {
    		switch ((String) response) {
			case "User not found": 
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			default:
				return new ResponseEntity<>("Unexpected error occured", HttpStatus.INTERNAL_SERVER_ERROR);
			}
    	}
    	logger.info("getUserProfile finished.");
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }
}