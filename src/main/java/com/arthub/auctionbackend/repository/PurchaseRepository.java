package com.arthub.auctionbackend.repository;

import com.arthub.auctionbackend.model.Purchase;
import com.arthub.auctionbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByBuyer(User buyer);
}
