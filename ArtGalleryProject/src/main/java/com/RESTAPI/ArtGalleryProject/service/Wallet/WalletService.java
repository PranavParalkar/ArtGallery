package com.RESTAPI.ArtGalleryProject.service.Wallet;

public interface WalletService {
	public Object getBalance(long userId);

	public Object updateBalance(long userId, double amount);
}
