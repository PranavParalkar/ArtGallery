package com.RESTAPI.ArtGalleryProject.service.DashBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.Entity.Painting;
import com.RESTAPI.ArtGalleryProject.repository.PaintingRepo;

@Service
public class DashboardService implements DashboardRoles{
	@Autowired
	private PaintingRepo paintingrepo;

	@Override
	public Page<Painting> getPaintingsByPage(int pageNo, int size) {
		return paintingrepo.findByIsSoldFalse(PageRequest.of(pageNo, size));
	}

	@Override
	public Painting getPaintingById(long id) {
		return paintingrepo.findById(id).orElse(null);
	}
	
}
