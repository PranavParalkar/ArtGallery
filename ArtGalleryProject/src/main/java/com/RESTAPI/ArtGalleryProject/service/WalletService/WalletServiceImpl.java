package com.RESTAPI.ArtGalleryProject.service.WalletService;

import java.util.HashMap;
import java.util.Map;

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
	public void incrementBalanceByEmail(long userId, double amount) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
		Wallet wallet = walletRepo.findByUser(user)
				.orElseThrow(() -> new EntityNotFoundException("Wallet not found for user with id: " + userId));
		wallet.setBalance(wallet.getBalance() + amount);
		walletRepo.save(wallet);
	}

	@Override
	public Map<String, Object> getBalance(long userId) {
		logger.info("Fetching wallet balance for User ID: {}", userId);
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
		Wallet wallet = walletRepo.findByUser(user)
				.orElseThrow(() -> new EntityNotFoundException("Wallet not found for user with id: " + userId));

		Map<String, Object> response = new HashMap<>();
		response.put("balance", wallet.getBalance());
		return response;
	}
}