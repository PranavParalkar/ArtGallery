package com.RESTAPI.ArtGalleryProject.service.DashBoard;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.DTO.DashBoard.UserDetailsResponse;
import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
    private UserRepo userRepo;

	@Override
	public Object getUserDetials(long userId, String email) {
		logger.info("getUserDetials started.");
		Optional<User> userOpt = userRepo.findById(userId);
		if(userOpt.isEmpty()) {
			return "User not found";
		}
		User user = userOpt.get();
		UserDetailsResponse response = new UserDetailsResponse(
				user.getAddress(),
				user.getName(),
				email,
				user.getPhoneNumber(),
				user.getCreatedAt(),
				user.getPaintingsSold(), 
				user.getPaintingsBought());
		logger.info("getUserDetials finished.");
		return response;
	}
	
	
}
