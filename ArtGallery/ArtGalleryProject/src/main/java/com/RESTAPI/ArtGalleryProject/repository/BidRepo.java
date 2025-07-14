package com.RESTAPI.ArtGalleryProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.RESTAPI.ArtGalleryProject.Entity.Bid;

@Repository
public interface BidRepo extends JpaRepository<Bid, Long> {
}
