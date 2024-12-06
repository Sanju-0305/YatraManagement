package com.customerservice.serviceImpl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl {
	@Autowired
    private JavaMailSender mailSender;
	
	 public void sendSimpleMessage(String to, String subject, String text) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom("sannitvin@gmail.com"); // Use the sender's email
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(text);
	        
	        mailSender.send(message); // Sends the email
	    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setFrom("your-email@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // Set as HTML content
        
        mailSender.send(message);
    }
    
    public void sendEmailWithAttachment(String to, String subject, String text, File file) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setFrom("your-email@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        helper.addAttachment(file.getName(), file); // Add attachment
        
        mailSender.send(message);
    }
}

