package com.RESTAPI.ArtGalleryProject.controller.WalletController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.RESTAPI.ArtGalleryProject.Entity.Wallet;
import com.RESTAPI.ArtGalleryProject.repository.WalletRepo;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletRepo walletRepo;

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateWalletBalance(@PathVariable Long userId, @RequestBody Wallet updatedWallet) {
        Wallet wallet = walletRepo.findById(userId).orElse(null);
        if (wallet == null) {
            return new ResponseEntity<>("Wallet not found", HttpStatus.NOT_FOUND);
        }

        wallet.setBalance(updatedWallet.getBalance());
        walletRepo.save(wallet);
        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getWallet(@PathVariable Long userId) {
        Wallet wallet = walletRepo.findById(userId).orElse(null);
        if (wallet == null) {
            return new ResponseEntity<>("Wallet not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }
}
