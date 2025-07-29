package com.RESTAPI.ArtGalleryProject.controller.PaintingController;

import com.RESTAPI.ArtGalleryProject.DTO.DashBoard.PlaceBidRequest;
import com.RESTAPI.ArtGalleryProject.DTO.DashBoard.TopBidDTO;
import com.RESTAPI.ArtGalleryProject.security.AuthHelper;
import com.RESTAPI.ArtGalleryProject.service.DashBoard.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/auctions")
public class BidController {

	private static final Logger logger = LoggerFactory.getLogger(BidController.class);

	@Autowired
	private AuthHelper authHelper;
	@Autowired
	private BidService service;

	@PostMapping("/bid/{paintingId}")
	public ResponseEntity<?> placeBidcont(@PathVariable long paintingId, @RequestBody PlaceBidRequest request) {
		try {
			long userId = authHelper.getCurrentUserId();
			service.placeBid(userId, paintingId, request.bidAmount());
			logger.info("placeBidcont finished.");
			return ResponseEntity.ok("Bid placed successfully");
		} catch (RuntimeException e) {
			logger.info("placeBidcont finished with error.");
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/bid/{paintingId}")
	public ResponseEntity<List<TopBidDTO>> getTop3Bids(@PathVariable Long paintingId) {
		logger.info("getTop3Bids started.");
		List<TopBidDTO> topBids = service.getTop3BidsWithRank(paintingId);
		logger.info("getTop3Bids finished.");
		return ResponseEntity.ok(topBids);

	}

//	@GetMapping("/not-live")
//	public ResponseEntity<?> auctionEnded() {
////		logger.info("auctionEnded called.");
////		String response = 
////		switch () {
////		case value: {
////			
////			yield type;
////		}
////		default:
////			throw new IllegalArgumentException("Unexpected value: " + );
////		}
//	}
//	
//	@GetMapping("/live")
//	public ResponseEntity<?> auctionStarted() {
////		logger.info("auctionEnded called.");
////		String response = 
////		switch () {
////		case value: {
////			
////			yield type;
////		}
////		default:
////			throw new IllegalArgumentException("Unexpected value: " + );
////		}
//	}
}
