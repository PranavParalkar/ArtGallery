package com.RESTAPI.ArtGalleryProject.repository;

import com.RESTAPI.ArtGalleryProject.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    Transaction findByPaymentGatewayTxnId(String txnId);
}
	