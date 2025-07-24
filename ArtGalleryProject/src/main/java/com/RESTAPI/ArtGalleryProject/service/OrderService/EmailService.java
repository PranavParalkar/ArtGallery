package com.RESTAPI.ArtGalleryProject.service.OrderService;

public interface EmailService {
	public void sendOrderConfirmationEmail(String senderEmail, String subject, String body);
}
