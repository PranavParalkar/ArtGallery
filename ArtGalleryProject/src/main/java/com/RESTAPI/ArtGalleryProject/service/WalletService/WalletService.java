package com.RESTAPI.ArtGalleryProject.service.WalletService;

import java.util.Map;

public interface WalletService {
	Map<String, Object> getBalance(String email);
	void incrementBalanceByEmail(String email, double amount);
	void decrementBalanceByEmail(String email, double amount);
}
