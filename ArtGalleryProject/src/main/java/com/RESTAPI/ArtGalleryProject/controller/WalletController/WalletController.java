package com.RESTAPI.ArtGalleryProject.controller.WalletController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.RESTAPI.ArtGalleryProject.DTO.DashBoard.updateBalanceRequest;
import com.RESTAPI.ArtGalleryProject.security.AuthHelper;
import com.RESTAPI.ArtGalleryProject.service.Wallet.WalletService;
import com.RESTAPI.ArtGalleryProject.DTO.Payment.InitiatePaymentRequest;
import com.RESTAPI.ArtGalleryProject.service.PaymentService.PaymentService;

@RestController
@RequestMapping("/wallet")
public class WalletController {

	private static final Logger logger = LoggerFactory.getLogger(WalletController.class);
	
	@Autowired
	private AuthHelper authHelper;

	@Autowired
	private WalletService service;
	
    @Autowired
    private PaymentService paymentService;

	
    @GetMapping
    public ResponseEntity<?> getWallet() {
    	logger.info("getWallet started.");
    	long userId = authHelper.getCurrentUserId();
    	Object response = service.getBalance(userId);
    	if(response instanceof String) {
    		logger.info("getWallet finished.");
    		switch ((String)response) {
    		case "User doesn't exist":
    			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    		case "wallet doesn't exist":
    			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    		default:
    			return new ResponseEntity<>("Unexpected error occured", HttpStatus.INTERNAL_SERVER_ERROR);    			
    		}
    	}
    	logger.info("getWallet finished.");
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateWalletBalance(@RequestBody updateBalanceRequest request) {
    	logger.info("updateWalletBalance started.");
    	long userId = authHelper.getCurrentUserId();
    	Object response = service.updateBalance(userId, request.amount());
    	if(response instanceof String) {
    		logger.info("getWallet finished.");
    		switch ((String)response) {
    		case "User doesn't exist":
    			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    		case "wallet doesn't exist":
    			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    		default:
    			return new ResponseEntity<>("Unexpected error occured", HttpStatus.INTERNAL_SERVER_ERROR);    			
    		}
    	}
        logger.info("updateWalletBalance finished.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/update-status")
    public ResponseEntity<String> updatePaymentStatus(
            @RequestParam String txnId,
            @RequestParam boolean success) {

        paymentService.updateTransactionStatus(txnId, success);
        return ResponseEntity.ok("Updated");
    }
}
