package com.arthub.auctionbackend.repository;

import com.arthub.auctionbackend.model.Artwork;
import com.arthub.auctionbackend.model.Bid;
import com.arthub.auctionbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByArtworkOrderByAmountDesc(Artwork artwork);
    List<Bid> findByBidder(User bidder);
}
