package com.RESTAPI.ArtGalleryProject.service.WalletService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.Entity.Wallet;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;
import com.RESTAPI.ArtGalleryProject.repository.WalletRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class WalletServiceImpl implements WalletService {

	private static final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private WalletRepo walletRepo;

	@Override
	public void incrementBalanceByEmail(String email, double amount) {
		Wallet wallet = walletRepo.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("Wallet not found for user with email: " + email));
		wallet.setBalance(wallet.getBalance() + amount);
		walletRepo.save(wallet);
	}

	@Override
	public Map<String, Object> getBalance(String email) {
		logger.info("Fetching wallet balance for User Email: {}", email);
		Optional<Wallet> wallet = walletRepo.findByEmail(email);
		Map<String, Object> response = new HashMap<>();
		response.put("balance", wallet.get().getBalance());
		return response;
	}
}