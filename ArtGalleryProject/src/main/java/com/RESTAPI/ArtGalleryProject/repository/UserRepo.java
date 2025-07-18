package com.RESTAPI.ArtGalleryProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.RESTAPI.ArtGalleryProject.Entity.User;
import com.RESTAPI.ArtGalleryProject.Enum.LicenseStatus;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	 List<User> findByLicenseStatus(LicenseStatus status);
}


