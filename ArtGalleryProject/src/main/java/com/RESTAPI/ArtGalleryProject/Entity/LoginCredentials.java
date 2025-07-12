package com.RESTAPI.ArtGalleryProject.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class LoginCredentials {
	
	@Id
	private String email;
	private String password;
	private int securityQuesId;
	private String securityAnswer;
}
