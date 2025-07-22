package com.RESTAPI.ArtGalleryProject.service.DashBoard;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.RESTAPI.ArtGalleryProject.DTO.DashBoard.UserDetailsResponse;
import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.controller.DashBoard.UserController;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;

public class UserServiceImpl implements UserService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
    private UserRepo userRepo;

	@Override
	public UserDetailsResponse getUserDetials(long userId) {
		Optional<User> userOpt = userRepo.findById(userId);
		
	}
	
	
}
