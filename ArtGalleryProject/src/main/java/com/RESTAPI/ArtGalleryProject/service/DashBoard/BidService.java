package com.RESTAPI.ArtGalleryProject.service.DashBoard;

import com.RESTAPI.ArtGalleryProject.DTO.DashBoard.TopBidDTO;

import java.util.List;

public interface BidService {
    void placeBid(Long userId, Long paintingId, double amount);

    List<TopBidDTO> getTop3BidsWithRank(Long paintingId);
    
    String auctionEnds();
}
