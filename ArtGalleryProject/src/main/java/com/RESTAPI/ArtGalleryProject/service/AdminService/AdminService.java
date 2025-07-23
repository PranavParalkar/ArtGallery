package com.RESTAPI.ArtGalleryProject.service.AdminService;

import com.RESTAPI.ArtGalleryProject.Entity.UnverifiedPainting;

import java.util.List;

public interface AdminService {
    List<UnverifiedPainting> getPendingPaintings();
    String approvePainting(Long id);
    String rejectPainting(Long id);
}
