package com.RESTAPI.ArtGalleryProject.service.OrderService;

import com.RESTAPI.ArtGalleryProject.Entity.Painting;
import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.Enum.TransactionType;

public interface TransactionService {
	public void createTransaction(User user, TransactionType type, double amount, Painting painting);
	public void createTransaction(User user, TransactionType type, double amount);
}
