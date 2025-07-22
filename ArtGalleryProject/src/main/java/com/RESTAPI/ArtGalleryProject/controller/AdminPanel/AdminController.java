package com.RESTAPI.ArtGalleryProject.controller.AdminPanel;

import com.RESTAPI.ArtGalleryProject.Entity.Painting;
import com.RESTAPI.ArtGalleryProject.Entity.UnverifiedPainting;
import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.repository.PaintingRepo;
import com.RESTAPI.ArtGalleryProject.repository.UnverifiedPaintingRepo;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;
import com.RESTAPI.ArtGalleryProject.service.AdminService.AdminService;
import com.RESTAPI.ArtGalleryProject.service.LicenseUpload.LicenseUploadService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private LicenseUploadService licenseUploadService;

    @Autowired
    private UnverifiedPaintingRepo unverifiedRepo;

    @Autowired
    private PaintingRepo paintingRepo;

    @Autowired
    private UserRepo userRepo;

    //  LICENSE APPROVAL ENDPOINTS
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


    // 1. View all unverified paintings submitted by sellers
    @GetMapping("/paintings/unverified")
    public List<UnverifiedPainting> getUnverifiedPaintings() {
        logger.info("getUnverifiedPaintings called.");
        return unverifiedRepo.findByApprovedFalse();
    }

    // 2. Approve a painting → Move from UnverifiedPainting to Painting
    @PostMapping("/paintings/approve/{id}")
    public ResponseEntity<String> approvePainting(@PathVariable Long id) {
        logger.info("approvePainting started for id {}", id);

        var optional = unverifiedRepo.findById(id);
        if (optional.isEmpty()) return ResponseEntity.badRequest().body("Painting not found");

        var unverified = optional.get();
        var seller = userRepo.findById(unverified.getSellerId()).orElse(null);
        if (seller == null) return ResponseEntity.badRequest().body("Seller not found");

        var painting = new Painting();
        painting.setTitle(unverified.getTitle());
        painting.setDescription(unverified.getDescription());
        painting.setLength(unverified.getLength());
        painting.setBreadth(unverified.getBreadth());
        painting.setStartingPrice(unverified.getStartingPrice());
        painting.setImageUrl(unverified.getImageUrl());
        painting.setSeller(seller);
        painting.setSold(false);

        paintingRepo.save(painting);
        unverifiedRepo.deleteById(id);

        logger.info("approvePainting finished for id {}", id);
        return ResponseEntity.ok("Painting approved and moved to auction.");
    }

    // 3. Reject a painting → Simply delete from UnverifiedPainting table
    @PostMapping("/paintings/reject/{id}")
    public ResponseEntity<String> rejectPainting(@PathVariable Long id) {
        logger.info("rejectPainting started for id {}", id);
        if (!unverifiedRepo.existsById(id)) {
            return ResponseEntity.badRequest().body("Painting not found");
        }
        unverifiedRepo.deleteById(id);
        logger.info("rejectPainting finished for id {}", id);
        return ResponseEntity.ok("Painting rejected and deleted.");
    }
}
