package com.RESTAPI.ArtGalleryProject.service.DashBoard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.Entity.Painting;
import com.RESTAPI.ArtGalleryProject.config.CorsConfig;
import com.RESTAPI.ArtGalleryProject.repository.PaintingRepo;

@Service
public class DashboardServiceImpl implements DashboardService{
	
	private static final Logger logger = LoggerFactory.getLogger(DashboardServiceImpl.class);
	
	@Autowired
	private PaintingRepo paintingrepo;

	@Override
	public Page<Painting> getPaintingsByPage(int pageNo, int size) {
		logger.info("getPaintingsByPage started.");
		logger.info("getPaintingsByPage finished.");
		return paintingrepo.findByIsSoldFalse(PageRequest.of(pageNo, size));
	}

	@Override
	public Painting getPaintingById(long id) {
		logger.info("getPaintingById started.");
		logger.info("getPaintingById finished.");
		return paintingrepo.findById(id).orElse(null);
	}
	
}
