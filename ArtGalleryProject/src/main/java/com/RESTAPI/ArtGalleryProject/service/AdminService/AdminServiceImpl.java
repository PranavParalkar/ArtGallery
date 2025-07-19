package com.RESTAPI.ArtGalleryProject.service.AdminService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.Enum.LicenseStatus;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired
    private final UserRepo userRepo = null;

    @Override
    public List<User> getPendingLicenses() {
    	logger.info("getPendingLicenses started.");
    	logger.info("getPendingLicenses finished.");
        return userRepo.findByLicenseStatus(LicenseStatus.PENDING);
    }

    @Override
    public void approveLicense(Long userId) {
    	logger.info("approveLicense started.");
        User user = userRepo.findById(userId).orElseThrow();
        user.setLicenseStatus(LicenseStatus.APPROVED);
        user.setAuthorizedSeller(true);
        userRepo.save(user);
        logger.info("approveLicense finished.");
    }

    @Override
    public void rejectLicense(Long userId) {
    	logger.info("rejectLicense started.");
        User user = userRepo.findById(userId).orElseThrow();
        user.setLicenseStatus(LicenseStatus.REJECTED);
        user.setAuthorizedSeller(false);
        userRepo.save(user);
        logger.info("rejectLicense finished.");
    }
}
