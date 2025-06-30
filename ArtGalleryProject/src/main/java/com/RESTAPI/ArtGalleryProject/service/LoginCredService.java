package com.RESTAPI.ArtGalleryProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.Entity.LoginCredentials;
import com.RESTAPI.ArtGalleryProject.repository.LoginCredentialsRepo;

@Service
public class LoginCredService implements LoginCredentialsRoles {

	@Autowired
	private LoginCredentialsRepo loginrepo;

	@Override
	public LoginCredentials registerAccountLoginCredentials(LoginCredentials logincred) {
		loginrepo.save(logincred);
		return logincred;
	}

	@Override
	public String updateAccountLoginCredentials(LoginCredentials logincred, String newPassword) {
		if (loginrepo.existsById(logincred.getUsername())) {
			LoginCredentials toCheck = loginrepo.findById(logincred.getUsername()).orElse(null);
			if (toCheck != null && toCheck.getPassword().equals(logincred.getPassword())) {
				loginrepo.save(logincred);
				return "Username: " + logincred.getUsername() + " sucessfully changes password";
			} else {
				return "Invalid password!";
			}
		} else {
			return "User Not Found!";
		}
	}
	
	public boolean existsById(String username) {
		if(loginrepo.existsById(username)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String validateLogin(LoginCredentials logincred) {
		if(loginrepo.existsById(logincred.getUsername())) {
			String password = loginrepo.findById(logincred.getUsername()).orElse(null).getPassword();
			if(password.equals(logincred.getPassword())) {
				return "Login Successful";
			} else {
				return "Invalid Password";
			}
		} else {
			return "Invalid Username";
		}
	}

}
