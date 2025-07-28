package com.RESTAPI.ArtGalleryProject.service.loginANDsignup;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.DTO.JwtResponse;
import com.RESTAPI.ArtGalleryProject.DTO.LoginANDsignup.LoginRequest;
import com.RESTAPI.ArtGalleryProject.DTO.LoginANDsignup.SignupRequest;
import com.RESTAPI.ArtGalleryProject.DTO.LoginANDsignup.UserDetailRequest;
import com.RESTAPI.ArtGalleryProject.Entity.LoginCredentials;
import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.Entity.Wallet;
import com.RESTAPI.ArtGalleryProject.Enum.Role;
import com.RESTAPI.ArtGalleryProject.repository.LoginCredRepo;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;
import com.RESTAPI.ArtGalleryProject.repository.WalletRepo;
import com.RESTAPI.ArtGalleryProject.security.JwtService;

import jakarta.transaction.Transactional;

@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserRepo userrepo;
	@Autowired
	private LoginCredRepo loginrepo;
	@Autowired
	private WalletRepo walletrepo;

	@Override
	@Transactional
	public Object register(SignupRequest request) {
		logger.info("register started.");
		if (loginrepo.existsById(request.email())) {
			logger.info("register finished.");
			return "Account already exists";
		}

		// save new user
		var user = new User();
		user.setAuthorizedSeller(false);
		user.setCreatedAt(LocalDate.now());
		user.setRole(Role.ROLE_USER);
		user.setWallet(null);
		userrepo.save(user);

		// save wallet with user foreign key
		var wallet = new Wallet();
		wallet.setBalance(0.0);
		wallet.setEmail(request.email());
		walletrepo.save(wallet);

		// save user in wallet entity
		user.setWallet(null);
		userrepo.save(user);

		// save login credentials
		var logincred = new LoginCredentials();
		logincred.setEmail(request.email());
		logincred.setPassword(encoder.encode(request.password()));
		logincred.setSecurityQuestion(request.securityQuestion());
		logincred.setSecurityAnswer(encoder.encode(request.securityAnswer()));
		logincred.setUser(user);
		loginrepo.save(logincred);

		long userId = logincred.getUser().getUserId();
		String token = jwtService.generateToken(logincred.getEmail(), userId, Role.ROLE_USER);
		logger.info("register finished.");
		return new JwtResponse(token);
	}

	@Override
	public String acceptDetails(UserDetailRequest request, String email) {
		logger.info("acceptDetails started.");
		Optional<LoginCredentials> optionalCred = loginrepo.findById(email);
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
	public Object validateLogin(LoginRequest request) {
		logger.info("validateLogin started.");
		Optional<LoginCredentials> loginOpt = loginrepo.findById(request.email());
		if (loginOpt.isEmpty()) {
			logger.info("validateLogin finished.");
			return "Invalid Email";
		}

		LoginCredentials login = loginOpt.get();
		if (!encoder.matches(request.password(), login.getPassword())) {
			logger.info("validateLogin finished.");
			return "Invalid Password";
		}

		long userId = login.getUser().getUserId();
		String email = request.email();
		Role role = login.getUser().getRole();
		String token = jwtService.generateToken(email, userId, role);

		logger.info("validateLogin finished.");
		return new JwtResponse(token);

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
	public Object passwordReset(String Email, String newPassword, String confirmPassword) {
		logger.info("passwordReset started.");
		Optional<LoginCredentials> logincredOpt = loginrepo.findById(Email);
		if (logincredOpt.isEmpty()) {
			logger.info("passwordReset finished.");
			return "Email not found";
		}
		LoginCredentials logincred = logincredOpt.get();
		if (!newPassword.equals(confirmPassword)) {
			logger.info("passwordReset finished.");
			return "Passwords don't match";
		}
		if (encoder.matches(newPassword, logincred.getPassword())) {
			logger.info("passwordReset finished.");
			return "New password and current password are same";
		}

		logincred.setPassword(encoder.encode(newPassword));
		loginrepo.save(logincred);
		long userId = logincred.getUser().getUserId();
		String token = jwtService.generateToken(Email, userId, Role.ROLE_USER);
		logger.info("passwordReset finished.");
		return new JwtResponse(token);
	}

}
