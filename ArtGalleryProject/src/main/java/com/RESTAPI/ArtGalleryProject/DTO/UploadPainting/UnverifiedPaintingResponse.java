package com.RESTAPI.ArtGalleryProject.DTO.UploadPainting;

public record UnverifiedPaintingResponse(
        Long id,
        String imageUrl,
        String title,
        String description,
        double length,
        double breadth,
        double startingPrice,
        boolean forAuction,
        String sellerName
) {}
