package com.RESTAPI.ArtGalleryProject.service.loginANDsignup;

import java.time.LocalDate;
import java.util.Optional;

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
		if (loginrepo.existsById(logincred.getEmail())) {
			return "Account already exists";
		}

		logincred.setPassword(encoder.encode(logincred.getPassword()));
		logincred.setSecurityAnswer(encoder.encode(logincred.getSecurityAnswer()));
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
		if (loginrepo.existsById(logincred.getEmail())) {
			String password = loginrepo.findById(logincred.getEmail()).orElse(null).getPassword();
			if (encoder.matches(logincred.getPassword(), password)) {
				return "Login Successful";
			} else {
				return "Invalid Password";
			}
		} else {
			return "Invalid Email";
		}
	}

	
//	 <-----------FORGOT PASSWORD FUNCTIONS------------>
	
	@Override
	public String getSecurityQuestion(String Email) {
		Optional<LoginCredentials> logincred = loginrepo.findById(Email);
		if (logincred.isEmpty()) {
			return "Invalid Email";
		} else {
			return logincred.get().getSecurityQuestion();
		}
	}

	@Override
	public String checkSecurityAnswer(String Email, String Answer) {
		Optional<LoginCredentials> logincred = loginrepo.findById(Email);
		if (logincred.isEmpty()) {
			return "Email not found";
		}
		if (encoder.matches(Answer, logincred.get().getSecurityAnswer())) {
			return "Verification Success";
		} else {
			return "Incorrect Answer";
		}

	}

	@Override
	public String passwordReset(String Email, String newPassword, String confirmPassword) {
		Optional<LoginCredentials> logincred = loginrepo.findById(Email);
		if (logincred.isEmpty()) {
			return "Email not found";
		}
		if (!newPassword.equals(confirmPassword)) {
			return "Passwords don't match";
		}
		if (encoder.matches(newPassword, logincred.get().getPassword())) {
			return "New password and current password are same";
		}
		logincred.get().setPassword(encoder.encode(newPassword));
		loginrepo.save(logincred.get());
		return "Password changed successfully";
	}

}
