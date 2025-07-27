package com.RESTAPI.ArtGalleryProject.controller.WalletController;

import com.RESTAPI.ArtGalleryProject.repository.WalletRepo;
import com.RESTAPI.ArtGalleryProject.repository.WithdrawalRequestRepo;
import com.RESTAPI.ArtGalleryProject.Entity.Wallet;
import com.RESTAPI.ArtGalleryProject.Entity.WithdrawalRequest;
import com.RESTAPI.ArtGalleryProject.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WalletController {

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private WithdrawalRequestRepo withdrawalRequestRepo;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/wallet/test")
    public ResponseEntity<?> testWallet() {
        return ResponseEntity.ok().body("Wallet controller is working!");
    }

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

    @PostMapping("/wallet/withdraw")
    public ResponseEntity<?> createWithdrawalRequest(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, Object> request) {
        
        try {
            String email = extractEmailFromToken(authHeader);
            if (email == null) {
                return ResponseEntity.status(401).body("Unauthorized");
            }

            System.out.println("Creating withdrawal request for email: " + email);
            System.out.println("Request data: " + request);

            // Extract fields without validation
            Double amount = Double.parseDouble(request.get("amount").toString());
            String bankAccount = request.get("bankAccount").toString();
            String ifscCode = request.get("ifscCode").toString();
            String accountHolderName = request.get("accountHolderName").toString();

            // Create withdrawal request without balance checks
            WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
            withdrawalRequest.setUserId(1L); // Default user ID
            withdrawalRequest.setUserEmail(email);
            withdrawalRequest.setAmount(amount);
            withdrawalRequest.setBankAccount(bankAccount);
            withdrawalRequest.setIfscCode(ifscCode);
            withdrawalRequest.setAccountHolderName(accountHolderName);
            withdrawalRequest.setStatus("PENDING");

            WithdrawalRequest savedRequest = withdrawalRequestRepo.save(withdrawalRequest);
            System.out.println("Withdrawal request saved with ID: " + savedRequest.getId());

            // Create response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Withdrawal request submitted successfully. Awaiting admin approval.");
            response.put("requestId", savedRequest.getId());
            response.put("amount", amount);
            response.put("status", "PENDING");
            response.put("estimatedTime", "3-5 business days after approval");

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            System.err.println("Error in createWithdrawalRequest: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
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
