package com.arthub.auctionbackend.repository;

import com.arthub.auctionbackend.model.Artwork;
import com.arthub.auctionbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    List<Artwork> findBySeller(User seller);
}
