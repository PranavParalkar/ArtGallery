package com.RESTAPI.ArtGalleryProject.controller.AdminPanel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.service.AdminService.AdminService;
import com.RESTAPI.ArtGalleryProject.service.LicenseUpload.LicenseUploadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor

public class AdminController {
	
	 private final AdminService adminService = null;

	 @Autowired
	 private LicenseUploadService licenseUploadService;
	 
	    @GetMapping("/licenses/pending")
	    public List<User> getPendingLicenses() {
	        return adminService.getPendingLicenses();
	    }

	    @PostMapping("/licenses/approve/{userId}")
	    public ResponseEntity<String> approveLicense(@PathVariable Long userId) {
	        adminService.approveLicense(userId);
	        return ResponseEntity.ok("License approved.");
	    }

	    @PostMapping("/licenses/reject/{userId}")
	    public ResponseEntity<String> rejectLicense(@PathVariable Long userId) {
	        adminService.rejectLicense(userId);
	        return ResponseEntity.ok("License rejected.");
	    }
	    

	    @PostMapping("/upload/{userId}")
	    public String uploadLicense(@PathVariable Long userId,
	                                 @RequestParam("file") MultipartFile file) {
	        return licenseUploadService.uploadLicense(userId, file);
	    }
}
