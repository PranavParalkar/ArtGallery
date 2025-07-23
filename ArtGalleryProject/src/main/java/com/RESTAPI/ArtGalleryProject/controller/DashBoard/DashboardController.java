package com.RESTAPI.ArtGalleryProject.controller.DashBoard;

import org.springframework.web.bind.annotation.RestController;

import com.RESTAPI.ArtGalleryProject.DTO.UploadPainting.PagePaintingResponse;
import com.RESTAPI.ArtGalleryProject.DTO.UploadPainting.PaintingResponse;
import com.RESTAPI.ArtGalleryProject.service.DashBoard.DashboardService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DashboardController {

	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	private DashboardService service;

	@GetMapping("/auctions")
	public ResponseEntity<?> getPainting(@RequestParam(defaultValue = "0") int pageNo) {
		logger.info("getPainting started.");
		final int pageSize = 12;
		var allPaintings = service.getPaintingsByPageAuction(pageNo, pageSize);
		logger.info("getPainting finished.");
		return new ResponseEntity<>(allPaintings, HttpStatus.OK);
	}

	@GetMapping("/auctions/{paintingId}")
	public ResponseEntity<?> getPaintingById(@PathVariable long paintingId) {
		logger.info("getPaintingById started.");
		PaintingResponse painting = service.getPaintingById(paintingId);
		logger.info("getPaintingById finished.");
		if (painting != null)
			return new ResponseEntity<>(painting, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
