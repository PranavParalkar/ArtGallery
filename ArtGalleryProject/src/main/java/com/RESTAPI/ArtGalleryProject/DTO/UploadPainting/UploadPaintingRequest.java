package com.RESTAPI.ArtGalleryProject.DTO.UploadPainting;

import org.springframework.web.multipart.MultipartFile;

import com.RESTAPI.ArtGalleryProject.Embeddable.Dimensions;

public record UploadPaintingRequest(
	MultipartFile file,
	Dimensions dimensions,
	String title,
	String description,
	int startingPrice,
	long userId
) {}
