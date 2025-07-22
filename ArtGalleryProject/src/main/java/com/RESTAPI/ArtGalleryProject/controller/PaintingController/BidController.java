package com.RESTAPI.ArtGalleryProject.controller.PaintingController;

import com.RESTAPI.ArtGalleryProject.DTO.DashBoard.PlaceBidRequest;
import com.RESTAPI.ArtGalleryProject.DTO.DashBoard.TopBidDTO;
import com.RESTAPI.ArtGalleryProject.service.DashBoard.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/dash")
public class BidController {

    private static final Logger logger = LoggerFactory.getLogger(BidController.class);

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
            logger.info("placeBidcont finished with error.");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/bid/{paintingId}")
    public ResponseEntity<List<TopBidDTO>> getTop10Bids(@PathVariable Long paintingId) {
        logger.info("Fetching top 10 bids for painting ID: {}", paintingId);
        List<TopBidDTO> topBids = service.getTop10BidsWithRank(paintingId);
        return ResponseEntity.ok(topBids);


        
    }
    @GetMapping("/user-bids/{userId}/{paintingId}")
        public ResponseEntity<List<UserBidDTO>> getUserBids(
        @PathVariable Long userId,
        @PathVariable Long paintingId
        ) {
            logger.info("Fetching user bids for userId={} and paintingId={}", userId, paintingId);
            List<UserBidDTO> userBids = service.getUserBidsForPainting(userId, paintingId);
            return ResponseEntity.ok(userBids);
        }

}
