package com.RESTAPI.ArtGalleryProject.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class LoginCredentials {

    @Id
    private String email;

    private String username;
    private String password;

    private String securityQuestion;
    private String securityAnswer;
    private String userType = "buyer"; // default in Java side too
    @Transient  // ✅ This will not be stored in the DB
    private String confirmPassword;
    private String phoneNumber;
}
