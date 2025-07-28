package com.RESTAPI.ArtGalleryProject.service.WalletService;

import java.util.Map;

public interface WalletService {
	Map<String, Object> getBalance(long userId);
	void incrementBalanceByEmail(long userId, double amount);
}
