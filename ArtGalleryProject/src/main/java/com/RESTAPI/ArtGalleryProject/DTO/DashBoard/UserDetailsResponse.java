package com.RESTAPI.ArtGalleryProject.DTO.DashBoard;

import java.time.LocalDate;
import java.util.List;

import com.RESTAPI.ArtGalleryProject.Embeddable.Address;
import com.RESTAPI.ArtGalleryProject.Entity.Painting;

public record UserDetailsResponse(
		Address address,
		String name,
		String email,
		String phoneNumber,
		LocalDate createdAt,
		List<Painting> paintingsSold,
		List<Painting> paintingsBought
) {}
