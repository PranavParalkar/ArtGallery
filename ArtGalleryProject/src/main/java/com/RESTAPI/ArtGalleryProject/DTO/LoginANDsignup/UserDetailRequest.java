package com.RESTAPI.ArtGalleryProject.DTO.LoginANDsignup;

import com.RESTAPI.ArtGalleryProject.Embeddable.Address;

public record UserDetailRequest (
	String email,
	Address address,
	String name,
	String phoneNumber
) {}
