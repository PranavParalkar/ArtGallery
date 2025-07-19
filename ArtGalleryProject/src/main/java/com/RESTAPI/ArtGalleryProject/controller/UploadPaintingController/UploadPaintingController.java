package com.RESTAPI.ArtGalleryProject.controller.UploadPaintingController;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RESTAPI.ArtGalleryProject.DTO.UploadPainting.UploadPaintingRequest;
import com.RESTAPI.ArtGalleryProject.service.UploadPainting.UploadService;

@RestController
public class UploadPaintingController {

	@Autowired
	private UploadService service;

	@Value("${image.path}")
	private String path;

	@PostMapping(value = "/upload-painting", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadPainting(@ModelAttribute UploadPaintingRequest request){
		try {
			return new ResponseEntity<>(service.uploadPainting(path, request), HttpStatus.ACCEPTED);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
