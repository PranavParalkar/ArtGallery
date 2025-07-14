package com.RESTAPI.ArtGalleryProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.RESTAPI.ArtGalleryProject.Entity.LoginCredentials;
import com.RESTAPI.ArtGalleryProject.service.LoginRoles;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginRoles loginservice;

    private final String emailPattern = "^[a-zA-Z0-9.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9.-]+$";
    private final String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";

    // 🔐 REGISTER API
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody LoginCredentials loginCred) {

        String email = loginCred.getEmail();
        String password = loginCred.getPassword();
        String confirmPassword = loginCred.getConfirmPassword(); // ✅ Now available in body
        String username = loginCred.getUsername();
        String securityQ = loginCred.getSecurityQuestion();
        String securityA = loginCred.getSecurityAnswer();

        // 🔎 Validations
        if (email == null || !email.matches(emailPattern)) {
            return ResponseEntity.badRequest().body("Invalid email format");
        }

        if (password == null || !password.matches(passwordPattern)) {
            return ResponseEntity.badRequest().body(
                    "Password must contain at least 8 characters, including uppercase, lowercase, digit, and special character");
        }

        if (confirmPassword == null || !password.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Passwords don't match");
        }

        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Username is required");
        }

        if (securityQ == null || securityQ.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Security question is required");
        }

        if (securityA == null || securityA.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Security answer is required");
        }

        // 🧠 Register User
        String response = loginservice.register(loginCred);

        return switch (response) {
            case "Account already exists" -> ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            case "Registration Successful" -> ResponseEntity.ok(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        };
    }

    // 🔐 LOGIN API
    @PostMapping("/login")
    public ResponseEntity<?> validateLogin(@RequestBody LoginCredentials loginCred) {
        String email = loginCred.getEmail();
        String password = loginCred.getPassword();

        if (email == null || !email.matches(emailPattern)) {
            return ResponseEntity.badRequest().body("Invalid email format");
        }

        if (password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required");
        }

        String response = loginservice.validateLogin(loginCred);

        return switch (response) {
            case "Invalid Email" -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            case "Invalid Password" -> ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            case "Login Successful" -> ResponseEntity.ok(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        };
    }
}
