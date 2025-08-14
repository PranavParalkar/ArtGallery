package com.RESTAPI.ArtGalleryProject.service.OrderService;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.Entity.Painting;
import com.RESTAPI.ArtGalleryProject.Entity.Transactions;
import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.Enum.TransactionType;
import com.RESTAPI.ArtGalleryProject.repository.TransactionRepo;

@Service
public class TransactionServiceImpl implements TransactionService{

	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
	@Autowired
	private TransactionRepo transactionRepo;
	
	@Override
	public void createTransaction(User user, TransactionType type, double amount, Painting painting) {
		Transactions transaction = new Transactions();
		logger.info("New transaction created for user {}", user.getName());
		transaction.setUser(user);
		transaction.setType(type);
		transaction.setAmount(amount);
		transaction.setPainting(painting);
		transaction.setTimeStamp(LocalDateTime.now());
		transactionRepo.save(transaction);
		logger.info("Transaction saved for user {}", user.getName());
	}

	@Override
	public void createTransaction(User user, TransactionType type, double amount) {
	    createTransaction(user, type, amount, null);
	}
	
}
