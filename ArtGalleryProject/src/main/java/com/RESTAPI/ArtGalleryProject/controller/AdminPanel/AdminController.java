package com.RESTAPI.ArtGalleryProject.controller.AdminPanel;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.RESTAPI.ArtGalleryProject.config.CorsConfig;
import com.RESTAPI.ArtGalleryProject.service.AdminService.AdminService;
import com.RESTAPI.ArtGalleryProject.service.LicenseUpload.LicenseUploadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor

public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private final AdminService adminService;

	@Autowired
	private LicenseUploadService licenseUploadService;

	@GetMapping("/licenses/pending")
	public List<User> getPendingLicenses() {
		logger.info("getPendingLicenses started.");
		logger.info("getPendingLicenses finished.");
		return adminService.getPendingLicenses();
	}

	@PostMapping("/licenses/approve/{userId}")
	public ResponseEntity<String> approveLicense(@PathVariable Long userId) {
		logger.info("approveLicense started.");
		adminService.approveLicense(userId);
		logger.info("approveLicense finished.");
		return ResponseEntity.ok("License approved.");
	}

	@PostMapping("/licenses/reject/{userId}")
	public ResponseEntity<String> rejectLicense(@PathVariable Long userId) {
		logger.info("rejectLicense started.");
		adminService.rejectLicense(userId);
		logger.info("rejectLicense finished.");
		return ResponseEntity.ok("License rejected.");
	}

	@PostMapping("/upload/{userId}")
	public String uploadLicense(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
		logger.info("uploadLicense started.");
		logger.info("uploadLicense finished.");
		return licenseUploadService.uploadLicense(userId, file);
	}
}
