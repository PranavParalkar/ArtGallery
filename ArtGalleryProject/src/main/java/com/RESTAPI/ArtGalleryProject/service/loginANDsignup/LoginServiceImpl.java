package com.RESTAPI.ArtGalleryProject.service.loginANDsignup;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.RESTAPI.ArtGalleryProject.config.CorsConfig;
import com.RESTAPI.ArtGalleryProject.repository.LoginCredRepo;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;
import com.RESTAPI.ArtGalleryProject.repository.WalletRepo;

@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Autowired
	private UserRepo userrepo;
	@Autowired
	private LoginCredRepo loginrepo;
	@Autowired
	private WalletRepo walletrepo;

	@Override
	public String register(SignupRequest request) {
		logger.info("register started.");
		if (loginrepo.existsById(request.email())) {
			logger.info("register finished.");
			return "Account already exists";
		}

		var wallet = new Wallet();
		wallet.setBalance(0.0);

		var user = new User();
		user.setAuthorizedSeller(false);
		user.setCreatedAt(LocalDate.now());
		user.setWallet(wallet);

		var logincred = new LoginCredentials();
		logincred.setPassword(encoder.encode(request.password()));
		logincred.setSecurityAnswer(encoder.encode(request.securityAnswer()));
		logincred.setUser(user);
		logincred.setEmail(request.email());
		logincred.setSecurityQuestion(request.securityQuestion());

		walletrepo.save(wallet);
		userrepo.save(user);
		loginrepo.save(logincred);
		logger.info("register finished.");
		return "Registration Successful";
	}

	@Override
	public String acceptDetails(UserDetailRequest request) {
		logger.info("acceptDetails started.");
		Optional<LoginCredentials> optionalCred = loginrepo.findById(request.email());
		if (optionalCred.isEmpty()) {
			logger.info("acceptDetails finished.");
			return "User not Found";
		}

		var logincred = optionalCred.get();
		var user = logincred.getUser();
		user.setAddress(request.address());
		user.setName(request.name());
		user.setPhoneNumber(request.phoneNumber());
		userrepo.save(user);
		logger.info("acceptDetails finished.");
		return "User info saved";
	}

	@Override
	public String validateLogin(LoginRequest request) {
		logger.info("validateLogin started.");
		if (loginrepo.existsById(request.email())) {
			String password = loginrepo.findById(request.email()).orElse(null).getPassword();
			if (encoder.matches(request.password(), password)) {
				logger.info("validateLogin finished.");
				return "Login Successful";
			} else {
				logger.info("validateLogin finished.");
				return "Invalid Password";
			}
		} else {
			logger.info("validateLogin finished.");
			return "Invalid Email";
		}
	}

//	 <-----------FORGOT PASSWORD FUNCTIONS------------>

	@Override
	public String getSecurityQuestion(String Email) {
		logger.info("getSecurityQuestion started.");
		Optional<LoginCredentials> logincred = loginrepo.findById(Email);
		if (logincred.isEmpty()) {
			logger.info("getSecurityQuestion finished.");
			return "Invalid Email";
		} else {
			logger.info("getSecurityQuestion finished.");
			return logincred.get().getSecurityQuestion();
		}
	}

	@Override
	public String checkSecurityAnswer(String Email, String Answer) {
		logger.info("checkSecurityAnswer started.");
		Optional<LoginCredentials> logincred = loginrepo.findById(Email);
		if (logincred.isEmpty()) {
			logger.info("checkSecurityAnswer finished.");
			return "Email not found";
		}
		if (encoder.matches(Answer, logincred.get().getSecurityAnswer())) {
			logger.info("checkSecurityAnswer finished.");
			return "Verification Success";
		} else {
			logger.info("checkSecurityAnswer finished.");
			return "Incorrect Answer";
		}

	}

	@Override
	public String passwordReset(String Email, String newPassword, String confirmPassword) {
		logger.info("passwordReset started.");
		Optional<LoginCredentials> logincred = loginrepo.findById(Email);
		if (logincred.isEmpty()) {
			logger.info("passwordReset finished.");
			return "Email not found";
		}
		if (!newPassword.equals(confirmPassword)) {
			logger.info("passwordReset finished.");
			return "Passwords don't match";
		}
		if (encoder.matches(newPassword, logincred.get().getPassword())) {
			logger.info("passwordReset finished.");
			return "New password and current password are same";
		}
		logincred.get().setPassword(encoder.encode(newPassword));
		loginrepo.save(logincred.get());
		logger.info("passwordReset finished.");
		return "Password changed successfully";
	}

}
