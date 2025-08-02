package com.RESTAPI.ArtGalleryProject.controller.WalletController;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RESTAPI.ArtGalleryProject.Entity.WithdrawalRequest;
import com.RESTAPI.ArtGalleryProject.repository.WithdrawalRequestRepo;
import com.RESTAPI.ArtGalleryProject.security.AuthHelper;
import com.RESTAPI.ArtGalleryProject.service.WalletService.WalletService;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private WithdrawalRequestRepo withdrawalRequestRepo;
    @Autowired
    private AuthHelper authHelper;
    @Autowired
    private WalletService walletService;


    @GetMapping("/test")
    public ResponseEntity<?> testWallet() {
        logger.info("GET /wallet/test endpoint hit. Wallet controller is responsive.");
        return ResponseEntity.ok().body("Wallet controller is working!");
    }

    @GetMapping
    public ResponseEntity<?> getWallet() {
        String email = authHelper.getCurrentEmail();
        logger.info("GET /wallet - Request received for user: {}", email);
        
        Map<String, Object> map = walletService.getBalance(email);
        
        logger.info("Successfully retrieved wallet balance for user: {}", email);
        return ResponseEntity.ok().body(map);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> createWithdrawalRequest(@RequestBody Map<String, Object> request) {
        
        logger.info("POST /wallet/withdraw - Received new withdrawal request.");
        logger.debug("Request payload: {}", request);

        String email = authHelper.getCurrentEmail();
        long userId = authHelper.getCurrentUserId();
        try {

            logger.info("Processing withdrawal request for user: {}", email);

            Double amount = Double.parseDouble(request.get("amount").toString());
            String bankAccount = request.get("bankAccount").toString();
            String ifscCode = request.get("ifscCode").toString();
            String accountHolderName = request.get("accountHolderName").toString();
            
            logger.debug("Parsed withdrawal details - Amount: {}, Bank Account: {}, IFSC: {}, Holder Name: {}", 
                         amount, "******", ifscCode, accountHolderName);

            WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
            withdrawalRequest.setUserId(1L);
            withdrawalRequest.setUserEmail(email);
            withdrawalRequest.setAmount(amount);
            withdrawalRequest.setBankAccount(bankAccount);
            withdrawalRequest.setIfscCode(ifscCode);
            withdrawalRequest.setAccountHolderName(accountHolderName);
            withdrawalRequest.setStatus("PENDING");

            logger.info("Saving withdrawal request for user: {}", email);
            WithdrawalRequest savedRequest = withdrawalRequestRepo.save(withdrawalRequest);
            logger.info("Successfully saved withdrawal request with ID: {} for user: {}", savedRequest.getId(), email);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Withdrawal request submitted successfully. Awaiting admin approval.");
            response.put("requestId", savedRequest.getId());
            response.put("amount", amount);
            response.put("status", "PENDING");
            response.put("estimatedTime", "3-5 business days after approval");

            return ResponseEntity.ok().body(response);

        } catch (NumberFormatException e) {
            logger.error("Error in createWithdrawalRequest for user: {}. Invalid 'amount' format in request.", email, e);
            return ResponseEntity.status(400).body("Bad Request: Invalid format for 'amount'.");
        } catch (Exception e) {
            logger.error("Error creating withdrawal request for user: {}", (email != null ? email : "unknown"), e);
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

}