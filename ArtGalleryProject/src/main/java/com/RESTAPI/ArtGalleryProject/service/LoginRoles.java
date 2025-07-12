package com.RESTAPI.ArtGalleryProject.service;

import com.RESTAPI.ArtGalleryProject.Entity.LoginCredentials;

public interface LoginRoles {
	public String register(LoginCredentials logincred);
	public String validateLogin(LoginCredentials logincred);
	public boolean existsById(String Email);
}
