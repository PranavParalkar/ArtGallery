package com.RESTAPI.ArtGalleryProject.service.LicenseUpload;

import org.springframework.web.multipart.MultipartFile;

public interface LicenseUploadService {
    String uploadLicense(Long userId, MultipartFile file);
}
