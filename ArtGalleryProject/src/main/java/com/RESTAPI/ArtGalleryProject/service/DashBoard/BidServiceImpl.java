package com.RESTAPI.ArtGalleryProject.service.DashBoard;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void placeBid(Long userId, Long paintingId, double newBidAmount) {

        User buyer = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Painting painting = paintingRepo.findById(paintingId)
                .orElseThrow(() -> new RuntimeException("Painting not found"));

        Wallet buyerWallet = buyer.getWallet();

        Optional<Bid> currentHighestBidOpt = bidRepo.findTopByPaintingOrderByBidAmountDescTimeStampAsc(painting);

        if (currentHighestBidOpt.isPresent()) {
            double currentHighest = currentHighestBidOpt.get().getBidAmount();
            if (newBidAmount <= currentHighest) {
                throw new RuntimeException("New bid must be strictly higher than current highest bid: " + currentHighest);
            }
        } else {
            double startingPrice = painting.getStartingPrice();
            if (newBidAmount < startingPrice) {
                throw new RuntimeException("First bid must be at least the starting price: " + startingPrice);
            }
        }

        if (buyerWallet.getBalance() < newBidAmount) {
            throw new RuntimeException("Insufficient wallet balance.");
        }

        // Refund previous highest bidder
        if (currentHighestBidOpt.isPresent()) {
            Bid prevBid = currentHighestBidOpt.get();
            Wallet prevWallet = prevBid.getBuyer().getWallet();
            prevWallet.setBalance(prevWallet.getBalance() + prevBid.getBidAmount());
            walletRepo.save(prevWallet);
        }

        // Deduct from current buyer
        buyerWallet.setBalance(buyerWallet.getBalance() - newBidAmount);
        walletRepo.save(buyerWallet);

        Bid newBid = new Bid();
        newBid.setBidAmount(newBidAmount);
        newBid.setBuyer(buyer);
        newBid.setPainting(painting);
        newBid.setTimeStamp(LocalTime.now());

        bidRepo.save(newBid);
    }

    @Override
    public List<TopBidDTO> getTop10BidsWithRank(Long paintingId) {
        List<Bid> topBids = bidRepo.findTop10ByPaintingIdOrderByBidAmountDesc(paintingId);
        List<TopBidDTO> result = new ArrayList<>();
        int rank = 1;
        for (Bid bid : topBids) {
            result.add(new TopBidDTO(rank++, bid.getBuyer().getName(), bid.getBidAmount()));
        }
        return result;
    }
}
