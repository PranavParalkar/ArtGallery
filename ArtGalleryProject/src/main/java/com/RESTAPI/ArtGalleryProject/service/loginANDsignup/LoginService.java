package com.RESTAPI.ArtGalleryProject.service.loginANDsignup;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.DTO.LoginANDsignup.LoginRequest;
import com.RESTAPI.ArtGalleryProject.DTO.LoginANDsignup.SignupRequest;
import com.RESTAPI.ArtGalleryProject.DTO.LoginANDsignup.UserDetailRequest;
import com.RESTAPI.ArtGalleryProject.Embeddable.Address;
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
	public String register(SignupRequest request) {
		if (loginrepo.existsById(request.email())) {
			return "Account already exists";
		}

		User user = new User();
		user.setAuthorizedSeller(false);
		user.setCreatedAt(LocalDate.now());
		userrepo.save(user);

		LoginCredentials logincred = new LoginCredentials();
		logincred.setPassword(encoder.encode(request.password()));
		logincred.setSecurityAnswer(encoder.encode(request.securityAnswer()));
		logincred.setUser(user);
		logincred.setEmail(request.email());
		logincred.setSecurityQuestion(request.securityQuestion());
		loginrepo.save(logincred);

		Wallet wallet = new Wallet();
		wallet.setBalance(0.0);
		wallet.setUser(user);
		walletrepo.save(wallet);

		return "Registration Successful";
	}

	@Override
	public String acceptDetails(UserDetailRequest request) {
		Optional<LoginCredentials> optionalCred = loginrepo.findById(request.email());
		if (optionalCred.isEmpty()) {
			return "User not Found";
		}

		LoginCredentials logincred = optionalCred.get();
		User user = logincred.getUser();
		user.setAddress(request.address());
		user.setName(request.name());
		user.setPhoneNumber(request.phoneNumber());
		userrepo.save(user);

		return "User info saved";
	}

	@Override
	public String validateLogin(LoginRequest request) {
		if (loginrepo.existsById(request.email())) {
			String password = loginrepo.findById(request.email()).orElse(null).getPassword();
			if (encoder.matches(request.password(), password)) {
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
