package com.RESTAPI.ArtGalleryProject.controller.UploadPaintingController;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RESTAPI.ArtGalleryProject.DTO.UploadPainting.UploadPaintingRequest;
import com.RESTAPI.ArtGalleryProject.service.UploadPainting.UploadService;

@RestController
public class UploadPaintingController {

	private static final Logger logger = LoggerFactory.getLogger(UploadPaintingController.class);
	
	@Autowired
	private UploadService service;

	@Value("${image.path}")
	private String path;

	@PostMapping("/upload-painting")
	public ResponseEntity<?> uploadPainting(@ModelAttribute UploadPaintingRequest request) {
		logger.info("uploadPainting started.");
		try {
			logger.info("uploadPainting finished.");
			return new ResponseEntity<>(service.uploadPainting(path, request), HttpStatus.ACCEPTED);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("uploadPainting finished.");
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
