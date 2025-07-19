package com.RESTAPI.ArtGalleryProject.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    // Allow all requests and enable CORS
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	logger.info("securityFilterChain started.");
    	http
        	.cors(Customizer.withDefaults()) // enables CORS with default settings
        	.csrf(csrf -> csrf.disable())    // disables CSRF
        	.authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll()		 // allows all requests
        	);

    	logger.info("securityFilterChain finished.");
        return http.build();
    }

    // BCrypt password encoder bean
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
