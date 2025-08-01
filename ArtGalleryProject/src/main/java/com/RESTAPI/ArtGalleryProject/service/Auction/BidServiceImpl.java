package com.RESTAPI.ArtGalleryProject.service.Auction;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.DTO.DashBoard.TopBidDTO;
import com.RESTAPI.ArtGalleryProject.Entity.Bid;
import com.RESTAPI.ArtGalleryProject.Entity.Painting;
import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.Entity.Wallet;
import com.RESTAPI.ArtGalleryProject.repository.BidRepo;
import com.RESTAPI.ArtGalleryProject.repository.PaintingRepo;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;
import com.RESTAPI.ArtGalleryProject.repository.WalletRepo;

import jakarta.transaction.Transactional;

@Service
public class BidServiceImpl implements BidService {

	private static final Logger logger = LoggerFactory.getLogger(BidServiceImpl.class);

	@Autowired
	private BidRepo bidRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PaintingRepo paintingRepo;
	@Autowired
	private WalletRepo walletRepo;

	@Override
	@Transactional
	public void placeBid(long userId, long paintingId, double newBidAmount) {
		logger.info("placeBid started.");

		User buyer = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		Painting painting = paintingRepo.findById(paintingId)
				.orElseThrow(() -> new RuntimeException("Painting not found"));

		Wallet buyerWallet = buyer.getWallet();
		Optional<Bid> currentHighestBidOpt = bidRepo.findTopByPaintingOrderByBidAmountDescTimeStampAsc(painting);

		if (currentHighestBidOpt.isPresent()) {
			double currentHighest = currentHighestBidOpt.get().getBidAmount();
			if (newBidAmount <= currentHighest) {
				logger.info("placeBid finished.");
				throw new RuntimeException(
						"New bid must be strictly higher than current highest bid: " + currentHighest);
			}
		} else {
			double startingPrice = painting.getStartingPrice();
			if (newBidAmount < startingPrice) {
				logger.info("placeBid finished.");
				throw new RuntimeException(
						"First bid must be at least the starting price: " + startingPrice);
			}
		}

		// Find if the buyer already placed a bid on this painting
		Optional<Bid> existingUserBidOpt = bidRepo.findByPaintingAndBuyer(painting, buyer);

		if (existingUserBidOpt.isPresent()) {
			Bid existingBid = existingUserBidOpt.get();
			if (buyerWallet.getBalance() + existingBid.getBidAmount() < newBidAmount) {
				logger.info("placeBid finished.");
				throw new RuntimeException("Insufficient wallet balance.");
			}
			buyerWallet.setBalance(buyerWallet.getBalance() + existingBid.getBidAmount());
			buyerWallet.setBalance(buyerWallet.getBalance() - newBidAmount);

			existingBid.setBidAmount(newBidAmount);
			existingBid.setTimeStamp(LocalTime.now());

			walletRepo.save(buyerWallet);
			bidRepo.save(existingBid);
		} else {
			if (currentHighestBidOpt.isPresent()) {
				Bid prevBid = currentHighestBidOpt.get();
				Wallet prevWallet = prevBid.getBuyer().getWallet();
				prevWallet.setBalance(prevWallet.getBalance() + prevBid.getBidAmount());
				walletRepo.save(prevWallet);
			}

			// Deduct from current buyer
			buyerWallet.setBalance(buyerWallet.getBalance() - newBidAmount);

			Bid newBid = new Bid();
			newBid.setBidAmount(newBidAmount);
			newBid.setBuyer(buyer);
			newBid.setPainting(painting);
			newBid.setTimeStamp(LocalTime.now());

			walletRepo.save(buyerWallet);
			bidRepo.save(newBid);
		}

		logger.info("placeBid finished.");
	}

	@Override
	public List<TopBidDTO> getTop3BidsWithRank(Long paintingId) {
		logger.info("getTop3BidsWithRank started.");
		Painting painting = paintingRepo.findById(paintingId)
				.orElseThrow(() -> new RuntimeException("Painting not found"));
		List<Bid> topBids = bidRepo.findTop3ByPaintingOrderByBidAmountDesc(painting);
		List<TopBidDTO> result = new ArrayList<>();
		int rank = 1;
		for (Bid bid : topBids) {
			result.add(new TopBidDTO(rank++, bid.getBuyer().getName(), bid.getBidAmount()));
		}
		logger.info("getTop3BidsWithRank finished.");
		return result;
	}

	@Transactional
	@Override
	public String auctionEnds() {
		logger.info("auctionEnds started.");
		List<Painting> livePaintings = paintingRepo.findByIsSoldFalseAndIsForAuctionTrue();
		for (Painting painting : livePaintings) {
			Optional<Bid> highestBidderOpt = bidRepo.findTopByPaintingOrderByBidAmountDescTimeStampAsc(painting);
			if(highestBidderOpt.isEmpty()) {
				continue;
			}
			
			Bid highestBidder = highestBidderOpt.get();
			List<Bid> allBids = bidRepo.findByPainting(painting);
			for(Bid bid : allBids) {
				if(bid.getBidAmount() == highestBidder.getBidAmount()) {
					
				} else {
					
				}
			}
			painting.setSold(true);
			painting.setFinalPrice(highestBidder.getBidAmount());
			painting.setBuyer(highestBidder.getBuyer());
			paintingRepo.save(painting);
		}
	}

}
