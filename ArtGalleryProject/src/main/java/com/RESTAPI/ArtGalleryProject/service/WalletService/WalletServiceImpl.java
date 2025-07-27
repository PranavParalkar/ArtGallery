package com.RESTAPI.ArtGalleryProject.service.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RESTAPI.ArtGalleryProject.Entity.Wallet;
import com.RESTAPI.ArtGalleryProject.repository.WalletRepo;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepo walletRepo;

    @Override
    public void incrementBalanceByEmail(String email, double amount) {
        Wallet wallet = walletRepo.findByEmail(email);
        if (wallet != null) {
            wallet.setBalance(wallet.getBalance() + amount);
            walletRepo.save(wallet);
        } else {
            // Optionally create a new wallet if not found
            Wallet newWallet = new Wallet();
            newWallet.setEmail(email);
            newWallet.setBalance(amount);
            walletRepo.save(newWallet);
        }
    }

    @Override
    public void decrementBalanceByEmail(String email, double amount) {
        Wallet wallet = walletRepo.findByEmail(email);
        if (wallet != null) {
            if (wallet.getBalance() >= amount) {
                wallet.setBalance(wallet.getBalance() - amount);
                walletRepo.save(wallet);
            } else {
                throw new RuntimeException("Insufficient balance");
            }
        } else {
            throw new RuntimeException("Wallet not found");
        }
    }
}