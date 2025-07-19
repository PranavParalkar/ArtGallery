package com.RESTAPI.ArtGalleryProject.controller.PaintingController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.RESTAPI.ArtGalleryProject.DTO.DashBoard.PlaceBidRequest;
import com.RESTAPI.ArtGalleryProject.service.DashBoard.BidService;

@RestController
@RequestMapping("/dash")
public class BidController {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadPaintingController.class);
	
	@Autowired
	private BidService service;
	
	@PostMapping("/{id}")
    public ResponseEntity<?> placeBidcont(@PathVariable long id, @RequestBody PlaceBidRequest request) {
		logger.info("placeBidcont started.");
		try {
            service.placeBid(request.userid(), id, request.bidAmount());
            logger.info("placeBidcont finished.");
            return ResponseEntity.ok("Bid placed successfully");
        } catch (RuntimeException e) {
        	logger.info("placeBidcont finished.");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
