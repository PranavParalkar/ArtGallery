package com.RESTAPI.ArtGalleryProject.service.WalletService;

public interface WalletService {
    void incrementBalanceByEmail(String email, double amount);
    void decrementBalanceByEmail(String email, double amount);
}
