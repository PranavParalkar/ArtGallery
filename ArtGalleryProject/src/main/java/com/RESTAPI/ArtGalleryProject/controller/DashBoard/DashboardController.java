package com.RESTAPI.ArtGalleryProject.controller.DashBoard;

import org.springframework.web.bind.annotation.RestController;

import com.RESTAPI.ArtGalleryProject.Entity.Painting;
import com.RESTAPI.ArtGalleryProject.service.DashBoard.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DashboardController {

	@Autowired
	private DashboardService service;

	@GetMapping("/dash")
	public ResponseEntity<?> getPainting(@RequestParam(defaultValue = "0") int pageNo) {
		final int pageSize = 12;
		Page<Painting> allPaintings = service.getPaintingsByPage(pageNo, pageSize);
		if (!allPaintings.isEmpty())
			return new ResponseEntity<>(allPaintings, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/dash/{id}")
	public ResponseEntity<?> getPaintingById(@PathVariable(name = "id") int paintingId) {
		Painting painting = service.getPaintingById(paintingId);
		if (painting != null)
			return new ResponseEntity<>(painting, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
