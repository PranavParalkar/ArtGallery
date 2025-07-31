package com.RESTAPI.ArtGalleryProject.DTO.DashBoard;

public record PaintingCodRequest(
	long paintingId,
	double amount,
	String mobile,
	String paymentMode,
	String address,
	String name
) {}
