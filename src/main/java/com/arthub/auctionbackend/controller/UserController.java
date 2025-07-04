package com.arthub.auctionbackend.controller;

import com.arthub.auctionbackend.model.User;
import com.arthub.auctionbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // for frontend React.js
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // SIGNUP
    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Email already exists!";
        }
        userRepository.save(user);
        return "Signup successful!";
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody User loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
            return "Login successful!";
        }
        return "Invalid email or password!";
    }
}
