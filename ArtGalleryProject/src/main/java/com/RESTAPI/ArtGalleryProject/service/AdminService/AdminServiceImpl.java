package com.RESTAPI.ArtGalleryProject.service.AdminService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.Enum.LicenseStatus;
import com.RESTAPI.ArtGalleryProject.repository.UserRepo;
com.RESTAPI.ArtGalleryProject.Entity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepo userRepo = null;

    @Override
    public List<User> getPendingLicenses() {
        return userRepo.findByLicenseStatus(LicenseStatus.PENDING);
    }

    @Override
    public void approveLicense(Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        user.setLicenseStatus(LicenseStatus.APPROVED);
        user.setAuthorizedSeller(true);
        userRepo.save(user);
    }

    @Override
    public void rejectLicense(Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        user.setLicenseStatus(LicenseStatus.REJECTED);
        user.setAuthorizedSeller(false);
        userRepo.save(user);
    }
}
