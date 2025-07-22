package com.RESTAPI.ArtGalleryProject.controller.DashBoard;

import org.springframework.web.bind.annotation.RestController;

import com.RESTAPI.ArtGalleryProject.DTO.UploadPainting.PaintingResponse;
import com.RESTAPI.ArtGalleryProject.service.DashBoard.DashboardService;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
		Page<PaintingResponse> allPaintings = service.getPaintingsByPage(pageNo, pageSize);
		logger.info("getPainting finished.");
		if (!allPaintings.isEmpty())
			return new ResponseEntity<>(allPaintings, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/auctions/{id}")
	public ResponseEntity<?> getPaintingById(@PathVariable(name = "id") int paintingId) {
		logger.info("getPaintingById started.");
		PaintingResponse painting = service.getPaintingById(paintingId);
		logger.info("getPaintingById finished.");
		if (painting != null)
			return new ResponseEntity<>(painting, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// wallet amount
	/*
	 * @GetMapping("/wallet") public ResponseEntity<?> getWalletBalance() {
	 * logger.info("getWalletBalance stated.");
	 * 
	 * Object response = service.walletBalance(add user id after getting userid frmo
	 * authentication); if (response instanceof String) {
	 * logger.info("getWalletBalance finished."); switch ((String)response) { case
	 * "user not found": return new ResponseEntity<>(response,
	 * HttpStatus.NOT_FOUND); default: return new
	 * ResponseEntity<>("Internal server Error", HttpStatus.INTERNAL_SERVER_ERROR);
	 * } } logger.info("getWalletBalance finished."); return new
	 * ResponseEntity<>((double)response, HttpStatus.OK); }
	 */

}
