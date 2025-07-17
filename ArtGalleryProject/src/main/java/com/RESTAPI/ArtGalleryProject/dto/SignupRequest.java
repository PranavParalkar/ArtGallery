package com.RESTAPI.ArtGalleryProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
	private String email;
	private String password;
	private String confirmPassword;
	private String securityQuestion;
	private String securityAnswer;
}
