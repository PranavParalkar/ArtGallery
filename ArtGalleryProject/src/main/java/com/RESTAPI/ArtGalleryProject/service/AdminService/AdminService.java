package com.RESTAPI.ArtGalleryProject.service.AdminService;

import java.util.List;

import com.RESTAPI.ArtGalleryProject.DTO.UploadPainting.UnverifiedPaintingResponse;
import com.RESTAPI.ArtGalleryProject.Entity.WithdrawalRequest;

public interface AdminService {
    List<UnverifiedPaintingResponse> getPendingPaintings();
    String approvePainting(Long id);
    String rejectPainting(Long id);
    
    // Withdrawal Request methods
    List<WithdrawalRequest> getPendingWithdrawalRequests();
    String approveWithdrawalRequest(Long id);
    String rejectWithdrawalRequest(Long id);
}
