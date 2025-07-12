package com.RESTAPI.ArtGalleryProject.service;

import com.RESTAPI.ArtGalleryProject.Entity.LoginCredentials;

public interface LoginCredentialsRoles {
	public LoginCredentials register(LoginCredentials logincred);
	public String validateLogin(LoginCredentials logincred);
//	public String updateAccountLoginCredentials(LoginCredentials logincred, String newPassword);
	public boolean existsById(String username);
}
