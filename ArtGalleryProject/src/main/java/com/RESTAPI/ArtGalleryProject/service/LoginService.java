package com.RESTAPI.ArtGalleryProject.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.Entity.LoginCredentials;
import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.Entity.Wallet;
import com.RESTAPI.ArtGalleryProject.repository.LoginCredRepo;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;
import com.RESTAPI.ArtGalleryProject.repository.WalletRepo;

@Service
public class LoginService implements LoginRoles {

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Autowired
	private UserRepo userrepo;
	@Autowired
	private LoginCredRepo loginrepo;
	@Autowired
	private WalletRepo walletrepo;

	@Override
	public String register(LoginCredentials logincred) {
		logincred.setPassword(encoder.encode(logincred.getPassword()));
		loginrepo.save(logincred);

		User user = new User();
		user.setAuthorizedSeller(false);
		user.setCreatedAt(LocalDate.now());
		user.setUserEmail(logincred);
		userrepo.save(user);

		Wallet wallet = new Wallet();
		wallet.setBalance(0);
		wallet.setUser(user);
		walletrepo.save(wallet);

		return "Registration Successful";
	}

	@Override
	public String validateLogin(LoginCredentials logincred) {
		if(loginrepo.existsById(logincred.getEmail())) {
			String password = loginrepo.findById(logincred.getEmail()).orElse(null).getPassword();
			if(encoder.matches(logincred.getPassword(), password)) {	
				return "Login Successful";
			} else {
				return "Invalid Password";
			}
		} else {
			return "Invalid Email";
		}
	}

	@Override
	public boolean existsById(String Email) {
		return loginrepo.existsById(Email);
	}

}
