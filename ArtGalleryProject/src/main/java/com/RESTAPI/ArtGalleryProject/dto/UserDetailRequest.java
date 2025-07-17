package com.RESTAPI.ArtGalleryProject.dto;

import com.RESTAPI.ArtGalleryProject.Embeddable.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailRequest {
	String email;
	Address address;
	String name;
	String phoneNumber;
}
