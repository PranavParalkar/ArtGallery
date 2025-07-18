package com.RESTAPI.ArtGalleryProject.DTO.LoginANDsignup;

public record SignupRequest(
		String email, 
		String password,  
		String confirmPassword, 
		String securityQuestion,
		String securityAnswer
) {}