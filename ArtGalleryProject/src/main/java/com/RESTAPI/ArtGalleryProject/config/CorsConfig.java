package com.RESTAPI.ArtGalleryProject.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(CorsConfig.class);
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	logger.info("addCorsMappings started");
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOrigins("http://localhost:5173") // Your React app
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true); // For cookies or Authorization header
        logger.info("addCorsMappings ended");
    }
}
