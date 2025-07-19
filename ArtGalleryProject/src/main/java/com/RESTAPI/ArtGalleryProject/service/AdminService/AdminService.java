package com.RESTAPI.ArtGalleryProject.service.AdminService;

import java.util.List;

import com.RESTAPI.ArtGalleryProject.Entity.User;

public interface AdminService {
	 List<User> getPendingLicenses();
	 void approveLicense(Long userId);
	 void rejectLicense(Long userId);
}
