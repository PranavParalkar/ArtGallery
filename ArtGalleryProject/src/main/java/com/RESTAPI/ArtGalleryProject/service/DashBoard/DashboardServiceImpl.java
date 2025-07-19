package com.RESTAPI.ArtGalleryProject.service.DashBoard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.DTO.UploadPainting.PaintingResponse;
import com.RESTAPI.ArtGalleryProject.Entity.Painting;
import com.RESTAPI.ArtGalleryProject.repository.PaintingRepo;

@Service
public class DashboardServiceImpl implements DashboardService{
	
	private static final Logger logger = LoggerFactory.getLogger(DashboardServiceImpl.class);
	
	@Autowired
	private PaintingRepo paintingrepo;

	@Override
	public Page<PaintingResponse> getPaintingsByPage(int pageNo, int size) {
		logger.info("getPaintingsByPage started.");
		
		Pageable pageable = PageRequest.of(pageNo, size);
		Page<Painting> paintingsPage = paintingrepo.findAll(pageable);

		Page<PaintingResponse> responsepainting = paintingsPage.map(p -> new PaintingResponse(
	        p.getPaintingId(),
	        p.getImageUrl(),
	        p.getTitle(),
	        p.getDescription(),
	        p.getLength(),
	        p.getBreadth(),
	        p.getStartingPrice(),
	        p.getFinalPrice(),
	        p.isSold(),
	        p.getSeller().getName()
	    ));
		
		logger.info("getPaintingsByPage finished.");
		return  responsepainting;
	}

	@Override
	public PaintingResponse getPaintingById(long id) {
		logger.info("getPaintingById started.");
		Painting painting = paintingrepo.findById(id).orElse(null);
		if(painting==null) {
			logger.info("getPaintingById finished.");
			return null;
		}
		logger.info("getPaintingById finished.");
		return new PaintingResponse(
				painting.getPaintingId(),
		        painting.getImageUrl(),
		        painting.getTitle(),
		        painting.getDescription(),
		        painting.getLength(),
		        painting.getBreadth(),
		        painting.getStartingPrice(),
		        painting.getFinalPrice(),
		        painting.isSold(),
		        painting.getSeller().getName()
		);
	}
	
}
