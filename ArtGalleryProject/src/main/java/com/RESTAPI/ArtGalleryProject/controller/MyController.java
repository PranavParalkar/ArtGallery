package com.RESTAPI.ArtGalleryProject.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
	
	@GetMapping("/home")
	public String home() { //To check if the API is working
		return "This this home";
	}
	
	
}
