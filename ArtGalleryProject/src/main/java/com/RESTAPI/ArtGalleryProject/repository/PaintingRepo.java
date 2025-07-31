package com.RESTAPI.ArtGalleryProject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RESTAPI.ArtGalleryProject.Entity.Painting;

@Repository
public interface PaintingRepo extends JpaRepository<Painting, Long> {
    Page<Painting> findByIsSoldFalseAndIsForAuctionTrue(Pageable pageable);    // for Auctions
	Page<Painting> findByIsSoldFalseAndIsForAuctionFalse(Pageable pageable);
	List<Painting> findByIsSoldFalseAndIsForAuctionTrue(); 					   // for shop paintings
}