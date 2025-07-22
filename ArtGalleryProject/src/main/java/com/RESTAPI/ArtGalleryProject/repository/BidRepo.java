package com.RESTAPI.ArtGalleryProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.RESTAPI.ArtGalleryProject.Entity.Bid;
import com.RESTAPI.ArtGalleryProject.Entity.Painting;

@Repository
public interface BidRepo extends JpaRepository<Bid, Long> {
	Optional<Bid> findTopByPaintingOrderByBidAmountDescTimeStampAsc(Painting painting);

	List<Bid> findTop3ByPaintingOrderByBidAmountDesc(Painting painting);

	List<Bid> findByBuyerUserIdAndPaintingPaintingId(Long userId, Long paintingId); 

}
