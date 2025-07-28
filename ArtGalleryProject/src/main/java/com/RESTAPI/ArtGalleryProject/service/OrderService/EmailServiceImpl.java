package com.RESTAPI.ArtGalleryProject.service.OrderService;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String emailFrom;

	@Override
	public void sendOrderConfirmationEmailCOD(String toEmail, String subject, String htmlBody, String localImagePath)
			throws MessagingException {
		logger.info("sendOrderConfirmationEmail started.");
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setFrom(emailFrom);
		helper.setTo(toEmail);
		helper.setSubject(subject);
		helper.setText(htmlBody, true);

		FileSystemResource res = new FileSystemResource(new File(localImagePath));

		helper.addInline("paintingImage", res);

		logger.info("sendOrderConfirmationEmail finished.");
		mailSender.send(message);
	}
	
	@Override
	public void sendOrderConfirmationEmail(String emailTo, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailTo);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(emailFrom);

        mailSender.send(message);
    }
	
}
