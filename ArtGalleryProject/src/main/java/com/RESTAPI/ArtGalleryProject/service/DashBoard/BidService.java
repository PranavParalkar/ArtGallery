package com.RESTAPI.ArtGalleryProject.service.DashBoard;

public interface BidService {
	void placeBid(Long userId, Long paintingId, double amount);
}
