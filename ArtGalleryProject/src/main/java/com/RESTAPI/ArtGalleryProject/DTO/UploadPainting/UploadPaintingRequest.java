package com.RESTAPI.ArtGalleryProject.DTO.UploadPainting;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

public record UploadPaintingRequest(
		@NotBlank
		MultipartFile file,
		String title,
		String description,
		double length,
		double breadth,
		int startingPrice,
		long userId
) {}
