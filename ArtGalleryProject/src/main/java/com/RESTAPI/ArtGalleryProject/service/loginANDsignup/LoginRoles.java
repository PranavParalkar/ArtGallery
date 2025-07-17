package com.RESTAPI.ArtGalleryProject.service.loginANDsignup;

import com.RESTAPI.ArtGalleryProject.Entity.LoginCredentials;
import com.RESTAPI.ArtGalleryProject.dto.UserDetailRequest;

public interface LoginRoles {
	public String register(LoginCredentials logincred);
	public String validateLogin(LoginCredentials logincred);
	public String getSecurityQuestion(String Email);
	public String checkSecurityAnswer(String Email, String Answer);
	public String passwordReset(String Email, String newPassword, String confirmPassword);
	public String acceptDetails(UserDetailRequest request);
}
