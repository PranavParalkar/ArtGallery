package com.RESTAPI.ArtGalleryProject.service.Auction;

import com.RESTAPI.ArtGalleryProject.DTO.DashBoard.TopBidDTO;

import java.util.List;

public interface BidService {
    public void placeBid(long userId, long paintingId, double amount);
    public List<TopBidDTO> getTop3BidsWithRank(Long paintingId);
    public String auctionEnds();
}
