package com.RESTAPI.ArtGalleryProject.controller.AdminPanel;

import com.RESTAPI.ArtGalleryProject.Entity.UnverifiedPainting;
import com.RESTAPI.ArtGalleryProject.service.AdminService.AdminService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    // 1. View all unverified paintings submitted by sellers
    @GetMapping("/paintings/unverified")
    public List<UnverifiedPainting> getUnverifiedPaintings() {
        logger.info("getUnverifiedPaintings calleds.");
        return adminService.getPendingPaintings();
    }

    // 2. Approve a painting → Move from UnverifiedPainting to Painting
    @PostMapping("/paintings/approve/{id}")
    public ResponseEntity<String> approvePainting(@PathVariable Long id) {
        logger.info("approvePainting called for id {}", id);
        String result = adminService.approvePainting(id);
        return result.contains("not") ? ResponseEntity.badRequest().body(result) : ResponseEntity.ok(result);
    }

    // 3. Reject a painting → Delete from UnverifiedPainting table
    @PostMapping("/paintings/reject/{id}")
    public ResponseEntity<String> rejectPainting(@PathVariable Long id) {
        logger.info("rejectPainting called for id {}", id);
        String result = adminService.rejectPainting(id);
        return result.contains("not") ? ResponseEntity.badRequest().body(result) : ResponseEntity.ok(result);
    }
}