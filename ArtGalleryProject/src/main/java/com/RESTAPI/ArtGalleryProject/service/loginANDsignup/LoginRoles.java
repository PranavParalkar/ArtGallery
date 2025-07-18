package com.RESTAPI.ArtGalleryProject.service.loginANDsignup;

import com.RESTAPI.ArtGalleryProject.DTO.LoginANDsignup.LoginRequest;
import com.RESTAPI.ArtGalleryProject.DTO.LoginANDsignup.SignupRequest;
import com.RESTAPI.ArtGalleryProject.DTO.LoginANDsignup.UserDetailRequest;

public interface LoginRoles {
	public String register(SignupRequest request);
	public String validateLogin(LoginRequest request);
	public String getSecurityQuestion(String Email);
	public String checkSecurityAnswer(String Email, String Answer);
	public String passwordReset(String Email, String newPassword, String confirmPassword);
	public String acceptDetails(UserDetailRequest request);
}
