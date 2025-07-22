package com.RESTAPI.ArtGalleryProject.service.Wallet;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.Entity.Wallet;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;
import com.RESTAPI.ArtGalleryProject.repository.WalletRepo;

@Service
public class WalletServiceImpl implements WalletService{
	
	private static final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);
	
	@Autowired
    private WalletRepo walletRepo;
	@Autowired
	private UserRepo userRepo;
	
	
	@Override
	public Object getBalance(long userId) {
		logger.info("getBalance started.");
		Optional<User> userOpt = userRepo.findById(userId);
		if(userOpt.isEmpty()) {
			logger.info("getBalance finished.");
			return "User doesn't exist";
		}
		Wallet wallet = userOpt.get().getWallet();
		if(wallet == null) {
			logger.info("getBalance finished.");
			return "wallet doesn't exist";
		}
		logger.info("getBalance finished.");
		return wallet.getBalance();
	}


	@Override
	public Object updateBalance(long userId, double amount) {
		logger.info("getBalance started.");
		Optional<User> userOpt = userRepo.findById(userId);
		if(userOpt.isEmpty()) {
			logger.info("getBalance finished.");
			return "User doesn't exist";
		}
		Wallet wallet = userOpt.get().getWallet();
		if(wallet == null) {
			logger.info("getBalance finished.");
			return "wallet doesn't exist";
		}
		wallet.setBalance(wallet.getBalance()+amount);
		walletRepo.save(wallet);
		logger.info("getBalance finished.");
		return wallet;
	}
		
}
