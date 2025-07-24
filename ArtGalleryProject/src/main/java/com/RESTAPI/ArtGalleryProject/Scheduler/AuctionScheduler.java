package com.RESTAPI.ArtGalleryProject.Scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.RESTAPI.ArtGalleryProject.Entity.Painting;
import com.RESTAPI.ArtGalleryProject.repository.PaintingRepo;

@Component
@EnableScheduling
public class AuctionScheduler {

	private static final Logger logger = LoggerFactory.getLogger(AuctionScheduler.class);
	
    @Autowired
    private PaintingRepo paintingRepo;

    /**
     * Runs every Tuesday at 9:00 AM and starts the auction for all paintings marked as for auction.
     */
    @Scheduled(cron = "0 0 9 ? * TUE") // Every Tuesday 9:00 AM
    public void startAuctions() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime auctionEnd = now.plusHours(48); // Ends Thursday 9:00 AM

        List<Painting> toStart = paintingRepo.findByIsForAuctionTrueAndIsAuctionLiveFalse();

        for (Painting painting : toStart) {
            painting.setAuctionLive(true);
            painting.setAuctionEndTime(auctionEnd);
        }

        paintingRepo.saveAll(toStart);
        logger.info("Auction started for {} paintings at {}", toStart.size(), now);
    }

    /**
     * Runs every hour to close any auctions whose 48 hours have ended.
     */
    @Scheduled(fixedRate = 3600000) // Every hour
    public void closeAuctions() {
        LocalDateTime now = LocalDateTime.now();

        List<Painting> toClose = paintingRepo
                .findByIsForAuctionTrueAndIsAuctionLiveTrueAndAuctionEndTimeBefore(now);

        for (Painting painting : toClose) {
            painting.setAuctionLive(false);
            // Optional: process winner, mark isSold = true, etc.
        }

        paintingRepo.saveAll(toClose);
        logger.info("Auction closed for {} paintings at {}", toClose.size(), now);
    }
}
