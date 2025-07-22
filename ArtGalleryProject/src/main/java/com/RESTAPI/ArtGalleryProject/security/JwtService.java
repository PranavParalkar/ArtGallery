package com.RESTAPI.ArtGalleryProject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

	private final String SECRET_KEY = "your_super_secret_key"; // should be at least 256 bits for HS256
	private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

	private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	
	public String generateToken(String email, long userId) {
		return Jwts.builder()
	    .claims() 
	        .subject(email)
	        .issuedAt(new Date(System.currentTimeMillis()))
	        .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	        .and() 
	    .signWith(key, Jwts.SIG.HS256) 
	    .compact();
	}

	public String extractEmail(String token) {
		return extractAllClaims(token).getSubject();
	}

	public long extractUserId(String token) {
		return extractAllClaims(token).get("userId", Long.class);
	}

	private Claims extractAllClaims(String token) {
		Claims claims = Jwts.parser()
			    .verifyWith(key)
			    .build()
			    .parseSignedClaims(token)
			    .getPayload();
		
		return claims;
	}
}
