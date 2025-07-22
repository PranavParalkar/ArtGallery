package com.RESTAPI.ArtGalleryProject.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthFilter extends GenericFilter {
	private static final long serialVersionUID = -2684403536504146356L;
	
	@Autowired
    private JwtService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        String authHeader = httpReq.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String email = jwtService.extractEmail(token);
                long userId = jwtService.extractUserId(token);
                request.setAttribute("email", email);
                request.setAttribute("userId", userId);
            } catch (Exception e) {
                // invalid token, proceed without setting
            }
        }

        chain.doFilter(request, response);
    }
}
