package com.RESTAPI.ArtGalleryProject.service.DashBoard;

import com.RESTAPI.ArtGalleryProject.DTO.DashBoard.TopBidDTO;
import com.RESTAPI.ArtGalleryProject.DTO.DashBoard.UserBidDTO;

import java.util.List;

public interface BidService {
    void placeBid(Long userId, Long paintingId, double amount);

    List<TopBidDTO> getTop10BidsWithRank(Long paintingId);
    List<UserBidDTO> getUserBidsForPainting(Long userId, Long paintingId); 
}
