package com.RESTAPI.ArtGalleryProject.service.DashBoard;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.DTO.UploadPainting.PagePaintingResponse;
import com.RESTAPI.ArtGalleryProject.DTO.UploadPainting.PaintingResponse;
import com.RESTAPI.ArtGalleryProject.Entity.Bid;
import com.RESTAPI.ArtGalleryProject.Entity.LoginCredentials;
import com.RESTAPI.ArtGalleryProject.Entity.Painting;
import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.repository.BidRepo;
import com.RESTAPI.ArtGalleryProject.repository.LoginCredRepo;
import com.RESTAPI.ArtGalleryProject.repository.PaintingRepo;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;
import com.RESTAPI.ArtGalleryProject.service.OrderService.EmailService;

@Service
public class DashboardServiceImpl implements DashboardService{
	
	private static final Logger logger = LoggerFactory.getLogger(DashboardServiceImpl.class);
	
	@Autowired
	private PaintingRepo paintingRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private BidRepo bidRepo;
	@Autowired
	private LoginCredRepo loginCredRepo;
	@Autowired
	private EmailService emailService;

	@Override
	public PagePaintingResponse<PaintingResponse> getUpcomingPaintingsByPageAuction(int pageNo, int size) {
		logger.info("getPaintingsByPage started.");
		
		Pageable pageable = PageRequest.of(pageNo, size);
		Page<Painting> paintingsPage = paintingRepo.findByIsSoldFalseAndIsForAuctionTrue(pageable);

		Page<PaintingResponse> pageResult = paintingsPage.map(p -> new PaintingResponse(
		    p.getPaintingId(),
		    p.getImageUrl(),
		    p.getTitle(),
		    p.getDescription(),
		    p.getLength(),
		    p.getBreadth(),
		    p.getStartingPrice(),
		    p.isForAuction(),
		    p.isSold(),
		    p.getSeller().getName()
		));
		
		logger.info("getPaintingsByPage finished.");
		return new PagePaintingResponse<PaintingResponse>(
		        pageResult.getContent(),
		        pageResult.getNumber(),
		        pageResult.getSize(),
		        pageResult.getTotalElements(),
		        pageResult.getTotalPages(),
		        pageResult.isLast()
		    );
	}

	@Override
	public PagePaintingResponse<PaintingResponse> getPaintingsByPageShop(int pageNo, int size) {
logger.info("getPaintingsByPage started.");
		
		Pageable pageable = PageRequest.of(pageNo, size);
		Page<Painting> paintingsPage = paintingRepo.findByIsSoldFalseAndIsForAuctionFalse(pageable);

		Page<PaintingResponse> pageResult = paintingsPage.map(p -> new PaintingResponse(
	        p.getPaintingId(),
	        p.getImageUrl(),
	        p.getTitle(),
	        p.getDescription(),
	        p.getLength(),
	        p.getBreadth(),
	        p.getStartingPrice(),
	        p.isForAuction(),
	        p.isSold(),
	        p.getSeller().getName()
	    ));
		
		logger.info("getPaintingsByPage finished.");
		return new PagePaintingResponse<PaintingResponse>(
		        pageResult.getContent(),
		        pageResult.getNumber(),
		        pageResult.getSize(),
		        pageResult.getTotalElements(),
		        pageResult.getTotalPages(),
		        pageResult.isLast()
		    );
	}
	
	@Override
	public PaintingResponse getPaintingById(long id) {
		logger.info("getPaintingById started.");
		Painting painting = paintingRepo.findById(id).orElse(null);
		if(painting==null) {
			logger.info("getPaintingById finished.");
			return null;
		}
		logger.info("getPaintingById finished.");
		return new PaintingResponse(
				painting.getPaintingId(),
		        painting.getImageUrl(),
		        painting.getTitle(),
		        painting.getDescription(),
		        painting.getLength(),
		        painting.getBreadth(),
		        painting.getStartingPrice(),
		        painting.isForAuction(),
		        painting.isSold(),
		        painting.getSeller().getName()
		);
	}

	@Override
	public Object walletBalance(long id) {
		logger.info("walletBalance started.");
		Optional<User> userOptional = userRepo.findById(id);
		if(userOptional.isEmpty()) {
			logger.info("walletBalance finished.");
			return "user not found";
		}
		User user = userOptional.get();
		logger.info("walletBalance finished.");
		return user.getWallet().getBalance();
	}

	@Override
	public Object updateAuctionEndStatus() {
		logger.info("updateAuctionEndStatus started.");
		List<Painting> auctionPaintings = paintingRepo.findByIsSoldFalseAndIsForAuctionTrue();
		
		for(Painting thisPainting : auctionPaintings) {
			List<Bid> bids = bidRepo.findByPainting(thisPainting);
			
			if (!bids.isEmpty()) {
				// Find the highest bid
				Bid highestBid = bids.stream()
					.max((b1, b2) -> Double.compare(b1.getBidAmount(), b2.getBidAmount()))
					.orElse(null);
				
				if (highestBid != null) {
					// Mark painting as sold and set buyer
					thisPainting.setSold(true);
					thisPainting.setBuyer(highestBid.getBuyer());
					paintingRepo.save(thisPainting);
					
					// Send email to winner
					sendWinnerEmail(highestBid, thisPainting);
					
					// Send emails to all other bidders
					for (Bid bid : bids) {
						if (!bid.getBidId().equals(highestBid.getBidId())) {
							sendLoserEmail(bid, thisPainting, highestBid);
						}
					}
					
					logger.info("Auction ended for painting: {} - Winner: {} with bid: {}", 
						thisPainting.getTitle(), 
						highestBid.getBuyer().getName(), 
						highestBid.getBidAmount());
				}
			}
		}
		
		logger.info("updateAuctionEndStatus finished.");
		return "Auction status updated successfully";
	}
	
	private void sendWinnerEmail(Bid winningBid, Painting painting) {
		try {
			User winner = winningBid.getBuyer();
			Optional<LoginCredentials> loginCred = loginCredRepo.findById(String.valueOf(winner.getUserId()));
			
			if (loginCred.isPresent()) {
				String winnerEmail = loginCred.get().getEmail();
				String subject = "🎉 Congratulations! You Won the Auction!";
				String body = String.format(
					"Dear %s,\n\n" +
					"Congratulations! You have won the auction for the painting:\n\n" +
					"🎨 Title: %s\n" +
					"📏 Dimensions: %.1fcm x %.1fcm\n" +
					"💰 Your Winning Bid: ₹%.2f\n" +
	                "👨‍🎨 Artist/Seller: %s\n\n" +
					"Please complete your payment within 48 hours to secure your purchase.\n\n" +
					"Thank you for participating in our art auction!\n\n" +
					"Best regards,\nArt Gallery Team",
					winner.getName(),
					painting.getTitle(),
					painting.getLength(),
					painting.getBreadth(),
					winningBid.getBidAmount(),
					painting.getSeller().getName()
				);
				
				emailService.sendOrderConfirmationEmail(winnerEmail, subject, body);
				logger.info("Winner email sent to: {}", winnerEmail);
			}
		} catch (Exception e) {
			logger.error("Error sending winner email: {}", e.getMessage());
		}
	}
	
	private void sendLoserEmail(Bid losingBid, Painting painting, Bid winningBid) {
		try {
			User loser = losingBid.getBuyer();
			Optional<LoginCredentials> loginCred = loginCredRepo.findById(String.valueOf(loser.getUserId()));
			
			if (loginCred.isPresent()) {
				String loserEmail = loginCred.get().getEmail();
				String subject = "Auction Update - Painting Sold";
				String body = String.format(
					"Dear %s,\n\n" +
					"The auction for the painting you bid on has ended:\n\n" +
					"🎨 Title: %s\n" +
					"📏 Dimensions: %.1fcm x %.1fcm\n" +
					"💰 Your Bid: ₹%.2f\n" +
					"🏆 Winning Bid: ₹%.2f\n" +
					"👨‍🎨 Artist/Seller: %s\n\n" +
					"Unfortunately, your bid was not the highest. Don't worry - we have many more beautiful artworks available for auction!\n\n" +
					"Keep an eye on our upcoming auctions for more opportunities.\n\n" +
					"Best regards,\nArt Gallery Team",
					loser.getName(),
					painting.getTitle(),
					painting.getLength(),
					painting.getBreadth(),
					losingBid.getBidAmount(),
					winningBid.getBidAmount(),
					painting.getSeller().getName()
				);
				
				emailService.sendOrderConfirmationEmail(loserEmail, subject, body);
				logger.info("Loser email sent to: {}", loserEmail);
			}
		} catch (Exception e) {
			logger.error("Error sending loser email: {}", e.getMessage());
		}
	}
	
	
	
}
