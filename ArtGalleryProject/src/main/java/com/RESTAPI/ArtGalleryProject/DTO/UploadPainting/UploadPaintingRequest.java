package com.RESTAPI.ArtGalleryProject.DTO.UploadPainting;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadPaintingRequest {
	private MultipartFile file;
	private String title;
	private String description;
	private double length;
	private double breadth;
	private double startingPrice;
	private long userId;
}
