package com.RESTAPI.ArtGalleryProject.controller.WalletController;

import com.RESTAPI.ArtGalleryProject.repository.WalletRepo;
import com.RESTAPI.ArtGalleryProject.Entity.Wallet;
import com.RESTAPI.ArtGalleryProject.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WalletController {

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/wallet")
    public ResponseEntity<?> getWallet(@RequestHeader("Authorization") String authHeader) {
        String email = extractEmailFromToken(authHeader);
        if (email == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        Wallet wallet = walletRepo.findByEmail(email);
        double balance = (wallet != null) ? wallet.getBalance() : 0.0;
        return ResponseEntity.ok().body(java.util.Collections.singletonMap("balance", balance));
    }

    private String extractEmailFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        String token = authHeader.substring(7);
        try {
            io.jsonwebtoken.Claims claims = jwtService.extractAllClaims(token);
            return claims.get("email", String.class);
        } catch (Exception e) {
            return null;
        }
    }
}
