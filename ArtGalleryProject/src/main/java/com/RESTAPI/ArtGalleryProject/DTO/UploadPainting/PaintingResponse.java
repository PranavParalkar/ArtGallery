package com.RESTAPI.ArtGalleryProject.DTO.UploadPainting;

public record PaintingResponse (
		Long paintingId,
		String imageUrl,
		String title,
		String description,
		double length,
		double breadth,
		double startingPrice,
		double finalPrice,
		boolean isSold,
		String seller
){}
