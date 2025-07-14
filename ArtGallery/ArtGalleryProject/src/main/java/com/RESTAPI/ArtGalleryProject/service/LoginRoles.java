package com.RESTAPI.ArtGalleryProject.service;

import com.RESTAPI.ArtGalleryProject.Entity.LoginCredentials;

public interface LoginRoles {

    // ✅ Called during registration
    String register(LoginCredentials logincred);

    // ✅ Called during login
    String validateLogin(LoginCredentials logincred);

    // ✅ Called during forgot-password (step 1)
    String getSecurityQuestion(String email);

    // ✅ Called during forgot-password (step 2)
    String checkSecurityAnswer(String email, String answer);

    // ✅ Called during forgot-password (step 3)
    String passwordReset(String email, String newPassword, String confirmPassword);
}
