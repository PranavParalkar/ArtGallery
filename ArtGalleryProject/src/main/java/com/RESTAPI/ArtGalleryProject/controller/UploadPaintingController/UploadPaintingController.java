package com.RESTAPI.ArtGalleryProject.controller.UploadPaintingController;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.RESTAPI.ArtGalleryProject.DTO.UploadPainting.UploadPaintingRequest;
import com.RESTAPI.ArtGalleryProject.service.UploadPainting.UploadService;

@RestController
public class UploadPaintingController {

	@Autowired
	private UploadService service;

	@Value("${image.path}")
	private String path;

	@PostMapping("/upload-painting")
	public ResponseEntity<?> uploadPainting(
	        @ModelAttribute UploadPaintingRequest request
	){
		try {
			return new ResponseEntity<>(service.uploadPainting(path, request), HttpStatus.ACCEPTED);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
