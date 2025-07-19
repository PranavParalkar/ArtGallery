package com.RESTAPI.ArtGalleryProject.service.DashBoard;

import org.springframework.data.domain.Page;

import com.RESTAPI.ArtGalleryProject.DTO.UploadPainting.PaintingResponse;

public interface DashboardService {
	public Page<PaintingResponse> getPaintingsByPage(int pageNo, int size);
	public PaintingResponse getPaintingById(long id);
}
