package com.RESTAPI.ArtGalleryProject.service.WalletService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.Entity.Wallet;
import com.RESTAPI.ArtGalleryProject.repository.WalletRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class WalletServiceImpl implements WalletService {

	private static final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

	@Autowired
	private WalletRepo walletRepo;

	@Override
	public void incrementBalanceByEmail(String email, double amount) {
		logger.info("Attempting to increment balance for email: {} by amount: {}", email, amount);
		Wallet wallet = walletRepo.findByEmail(email)
				.orElseThrow(() -> {
					logger.warn("Wallet not found for email: {}", email);
					return new EntityNotFoundException("Wallet not found for user with email: " + email);
				});
		double oldBalance = wallet.getBalance();
		wallet.setBalance(oldBalance + amount);
		walletRepo.save(wallet);
		logger.info("Successfully incremented balance for email: {} from {} to {}", email, oldBalance, wallet.getBalance());
	}

	@Override
	public Map<String, Object> getBalance(String email) {
		logger.info("Fetching wallet balance for User Email: {}", email);
		Optional<Wallet> walletOptional = walletRepo.findByEmail(email);
		Map<String, Object> response = new HashMap<>();
		if (walletOptional.isPresent()) {
			Wallet wallet = walletOptional.get();
			response.put("balance", wallet.getBalance());
			logger.info("Wallet balance for email {}: {}", email, wallet.getBalance());
		} else {
			logger.warn("Wallet not found for email: {}", email);
			throw new EntityNotFoundException("Wallet not found for user with email: " + email);
		}
		return response;
	}

    @Override
    public void decrementBalanceByEmail(String email, double amount) {
        logger.info("Attempting to decrement balance for email: {} by amount: {}", email, amount);
        Optional<Wallet> walletOptional = walletRepo.findByEmail(email);
        
        if (walletOptional.isPresent()) {
            Wallet wallet = walletOptional.get();
            double currentBalance = wallet.getBalance();
            if (currentBalance >= amount) {
                wallet.setBalance(currentBalance - amount);
                walletRepo.save(wallet);
                logger.info("Successfully decremented balance for email: {} from {} to {}", email, currentBalance, wallet.getBalance());
            } else {
                logger.warn("Insufficient balance for email: {}. Attempted to decrement by {} but current balance is {}", email, amount, currentBalance);
                throw new RuntimeException("Insufficient balance");
            }
        } else {
            logger.warn("Wallet not found for email: {}", email);
            throw new RuntimeException("Wallet not found");
        }
    }
}