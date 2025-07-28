package com.RESTAPI.ArtGalleryProject.service.OrderService;

import jakarta.mail.MessagingException;

public interface EmailService {
	public void sendOrderConfirmationEmail(String senderEmail, String subject, String htmlContent, String localImageUrl) throws MessagingException;
}
