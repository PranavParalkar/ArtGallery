package com.RESTAPI.ArtGalleryProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.Entity.Buyer;
import com.RESTAPI.ArtGalleryProject.Entity.LoginCredentials;
import com.RESTAPI.ArtGalleryProject.Entity.Seller;
import com.RESTAPI.ArtGalleryProject.repository.BuyerRepo;
import com.RESTAPI.ArtGalleryProject.repository.LoginCredentialsRepo;
import com.RESTAPI.ArtGalleryProject.repository.SellerRepo;

@Service
public class LoginCredService implements LoginCredentialsRoles {

	@Autowired
	private LoginCredentialsRepo loginrepo;
	@Autowired
	private BuyerRepo buyerrepo;
	@Autowired
	private SellerRepo sellerrepo;

	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Encoder

	@Override
	public LoginCredentials register(LoginCredentials logincred) {
		logincred.setPassword(encoder.encode(logincred.getPassword())); // encode password
		loginrepo.save(logincred);

		if (logincred.getType().equalsIgnoreCase("buyer")) { // make a entry in buyer table
			Buyer buyer = new Buyer();
			buyer.setBuyerUsername(logincred.getUsername());
			buyer.setBuyerPassword(logincred.getPassword());
			buyerrepo.save(buyer);
		} else if (logincred.getType().equalsIgnoreCase("seller")) { // make a entry in seller table
			Seller seller = new Seller();
			seller.setSellerUsername(logincred.getUsername());
			seller.setSellerPassword(logincred.getPassword());
			sellerrepo.save(seller);
		}

		return logincred;
	}

	@Override
	public String validateLogin(LoginCredentials logincred) {
		if (loginrepo.existsById(logincred.getUsername())
				&& loginrepo.findById(logincred.getUsername()).orElse(null).getType().equals(logincred.getType())) {
			// check if username present in database... if does then check its type entered
			String password = loginrepo.findById(logincred.getUsername()).orElse(null).getPassword();
			if (encoder.matches(logincred.getPassword(), password)) {
				return "Login Successful";
			} else {
				return "Invalid Password";
			}
		} else {
			return "Invalid Username";
		}
	}

	/*
	 * @Override public String updateAccountLoginCredentials(LoginCredentials
	 * logincred, String newPassword) { if
	 * (loginrepo.existsById(logincred.getUsername())) { LoginCredentials toCheck =
	 * loginrepo.findById(logincred.getUsername()).orElse(null); if (toCheck != null
	 * && toCheck.getPassword().equals(logincred.getPassword())) {
	 * loginrepo.save(logincred); return "Username: " + logincred.getUsername() +
	 * " sucessfully changes password"; } else { return "Invalid password!"; } }
	 * else { return "User Not Found!"; } }
	 */

	public boolean existsById(String username) {
		if (loginrepo.existsById(username)) {
			return true;
		} else {
			return false;
		}
	}

}
