package com.RESTAPI.ArtGalleryProject.service;

import com.RESTAPI.ArtGalleryProject.Entity.LoginCredentials;
import com.RESTAPI.ArtGalleryProject.Entity.User;

public interface LoginRoles {
	public String register(LoginCredentials logincred);
	public String validateLogin(User user);	
}
