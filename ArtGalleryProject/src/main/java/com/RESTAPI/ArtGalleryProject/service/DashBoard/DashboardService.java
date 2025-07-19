package com.RESTAPI.ArtGalleryProject.service.DashBoard;

import org.springframework.data.domain.Page;

import com.RESTAPI.ArtGalleryProject.Entity.Painting;

public interface DashboardService {
	public Page<Painting> getPaintingsByPage(int pageNo, int size);
	public Painting getPaintingById(long id);
}
