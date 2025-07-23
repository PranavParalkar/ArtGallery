package com.RESTAPI.ArtGalleryProject.service.DashBoard;

import com.RESTAPI.ArtGalleryProject.DTO.UploadPainting.PagePaintingResponse;
import com.RESTAPI.ArtGalleryProject.DTO.UploadPainting.PaintingResponse;

public interface DashboardService {
	public PagePaintingResponse<PaintingResponse> getPaintingsByPage(int pageNo, int size);
	public PaintingResponse getPaintingById(long id);
	public Object walletBalance(long id);
}
