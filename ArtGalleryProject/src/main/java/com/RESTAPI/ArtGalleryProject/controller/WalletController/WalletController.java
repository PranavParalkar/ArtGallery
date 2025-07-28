package com.RESTAPI.ArtGalleryProject.controller.WalletController;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RESTAPI.ArtGalleryProject.security.AuthHelper;
import com.RESTAPI.ArtGalleryProject.service.WalletService.WalletService;

@RestController
public class WalletController {
	
	private static final Logger logger = LoggerFactory.getLogger(WalletController.class);
	
    @Autowired
    private AuthHelper authHelper;
    @Autowired
    private WalletService walletService;

    @GetMapping("/wallet")
    public ResponseEntity<?> getWallet() {
        long userId = authHelper.getCurrentUserId();
        Map<String, Object> map = walletService.getBalance(userId);
        return ResponseEntity.ok().body(map);
    }
}
