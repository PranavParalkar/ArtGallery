package com.RESTAPI.ArtGalleryProject.controller.LoginANDsignup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;
import com.RESTAPI.ArtGalleryProject.security.AuthHelper;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private AuthHelper authHelper;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        long userId = authHelper.getCurrentUserId();
    	return userRepo.findById(userId)
            .map(user -> ResponseEntity.ok(user))
            .orElse(ResponseEntity.notFound().build());
    }
}