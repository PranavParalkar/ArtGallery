package com.RESTAPI.ArtGalleryProject.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RESTAPI.ArtGalleryProject.Entity.Painting;

@Repository
public interface PaintingRepo extends JpaRepository<Painting, Long> {
    Page<Painting> findByIsForAuctionTrueAndIsAuctionLiveTrue(Pageable pageable);    // Live Auctions
    Page<Painting> findByIsForAuctionTrueAndIsAuctionLiveFalse(Pageable pageable);   // Upcoming Auctions
	Page<Painting> findByIsSoldFalseAndIsForAuctionFalse(Pageable pageable);// for shop paintings

	//for live bidding
	List<Painting> findByIsForAuctionTrueAndIsAuctionLiveFalse(); // For Tuesday start
    List<Painting> findByIsForAuctionTrueAndIsAuctionLiveTrueAndAuctionEndTimeBefore(LocalDateTime time); // For closing
}
