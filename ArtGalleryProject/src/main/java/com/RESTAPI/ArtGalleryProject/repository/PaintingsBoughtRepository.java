package com.RESTAPI.ArtGalleryProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RESTAPI.ArtGalleryProject.Entity.PaintingsBought;

@Repository
public interface PaintingsBoughtRepository extends JpaRepository<PaintingsBought, Long> {
}
