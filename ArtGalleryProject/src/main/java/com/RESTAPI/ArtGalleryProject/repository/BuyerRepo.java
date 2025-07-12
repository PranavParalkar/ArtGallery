package com.RESTAPI.ArtGalleryProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RESTAPI.ArtGalleryProject.Entity.Buyer;

@Repository
public interface BuyerRepo extends JpaRepository<Buyer, Long>{
}
