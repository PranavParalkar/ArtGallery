package com.RESTAPI.ArtGalleryProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RESTAPI.ArtGalleryProject.Entity.Wallet;

@Repository
public interface WalletRepo extends JpaRepository<Wallet, Long> {
    Wallet findByEmail(String email);
}
