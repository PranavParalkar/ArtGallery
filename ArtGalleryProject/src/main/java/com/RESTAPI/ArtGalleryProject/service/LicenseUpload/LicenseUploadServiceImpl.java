package com.RESTAPI.ArtGalleryProject.service.LicenseUpload;

import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.Enum.LicenseStatus;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LicenseUploadServiceImpl implements LicenseUploadService {

	private static final Logger logger = LoggerFactory.getLogger(LicenseUploadServiceImpl.class);
	
    private static final String UPLOAD_DIR = "uploads/licenses/";

    @Autowired
    private UserRepo userRepo;

    @Override
    public String uploadLicense(Long userId, MultipartFile file) {
    	logger.info("uploadLicense started.");
        try {
            User user = userRepo.findById(userId).orElseThrow();

            // Create directory if it doesn't exist
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Save the file with unique name
            String fileName = "license_" + userId + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.write(filePath, file.getBytes());

            // Update user license status
            user.setLicenseStatus(LicenseStatus.PENDING);
            // Optionally store file path in DB
            // user.setLicenseFilePath(filePath.toString());

            userRepo.save(user);
            logger.info("uploadLicense finished.");
            return "License uploaded successfully. Awaiting admin approval.";

        } catch (IOException e) {
        	logger.info("uploadLicense finished.");
            throw new RuntimeException("Failed to upload license", e);
        }
    }
}
