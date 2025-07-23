package com.RESTAPI.ArtGalleryProject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RESTAPI.ArtGalleryProject.Entity.Painting;

@Repository
public interface PaintingRepo extends JpaRepository<Painting, Long> {
	Page<Painting> findByIsSoldFalseAndIsForAuctionTrue(Pageable pageable);
	Page<Painting> findByIsSoldFalseAndIsForAuctionFalse(Pageable pageable);
}
