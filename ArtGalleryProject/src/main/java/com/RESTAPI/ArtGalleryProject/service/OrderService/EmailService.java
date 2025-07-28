package com.RESTAPI.ArtGalleryProject.service.OrderService;

import jakarta.mail.MessagingException;

public interface EmailService {
	public void sendOrderConfirmationEmailCOD(String senderEmail, String subject, String htmlContent, String localImageUrl) throws MessagingException;
	public void sendOrderConfirmationEmail(String emailTo, String subject, String body);
}
