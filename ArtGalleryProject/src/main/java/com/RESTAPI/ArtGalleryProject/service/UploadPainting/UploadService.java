package com.RESTAPI.ArtGalleryProject.service.UploadPainting;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.RESTAPI.ArtGalleryProject.DTO.UploadPainting.UploadPaintingRequest;

public interface UploadService {
	public String uploadPainting(String path, UploadPaintingRequest request) throws IOException;
}
