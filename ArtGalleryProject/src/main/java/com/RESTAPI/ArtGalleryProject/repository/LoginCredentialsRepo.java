package com.RESTAPI.ArtGalleryProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RESTAPI.ArtGalleryProject.Entity.LoginCredentials;

@Repository
public interface LoginCredentialsRepo extends JpaRepository<LoginCredentials, String>{
}
