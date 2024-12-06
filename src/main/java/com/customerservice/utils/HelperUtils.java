package com.customerservice.utils;

import org.springframework.stereotype.Component;

@Component
public class HelperUtils {

	public String getLinkForVerification(String email,String secretToken) {
		String link = "http://localhost:8080/api/user/verifyUser/"+email+"~"+secretToken;
		return link;
	}
	
	public String htmlMailDetails(String verifiyLink,String recipientName) {
		 String htmlContent = "<!DOCTYPE html>"
		            + "<html lang='en'>"
		            + "<head>"
		            + "<meta charset='UTF-8'>"
		            + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
		            + "<title>Welcome to Our Service</title>"
		            + "<style>"
		            + "body { font-family: Arial, sans-serif; background-color: #f9f9f9; color: #333; margin: 0; padding: 0; }"
		            + ".email-container { width: 100%; max-width: 600px; margin: 0 auto; background-color: #fff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); }"
		            + ".email-header { background-color: #4CAF50; color: white; padding: 20px; text-align: center; border-top-left-radius: 8px; border-top-right-radius: 8px; }"
		            + ".email-body { padding: 20px; text-align: left; }"
		            + ".email-footer { background-color: #f1f1f1; padding: 10px; text-align: center; font-size: 12px; color: #777; border-bottom-left-radius: 8px; border-bottom-right-radius: 8px; }"
		            + "a { color: #4CAF50; text-decoration: none; }"
		            + "</style>"
		            + "</head>"
		            + "<body>"
		            + "<div class='email-container'>"
		            + "<div class='email-header'><h2>Welcome to Our Service!</h2></div>"
		            + "<div class='email-body'>"
		            + "<p>Hello <strong>"+recipientName+"</strong>,</p>"
		            + "<p>Thank you for signing up for our service. We are excited to have you onboard!</p>"
		            + "<p>Please confirm your email address by clicking the link below:</p>"
		            + "<p><a href='"+verifiyLink+"' target='_blank'>Confirm your email address</a></p>"
		            + "<p>If you have any questions, feel free to contact our support team at vipinmaurya1204@gmail.com.</p>"
		            + "<p>Best regards,<br>Kartik Yatra Team</p>"
		            + "</div>"
		            + "<div class='email-footer'>"
		            + "<p>&copy; 2024 Kartik Yatra. All rights reserved.</p>"
		            + "</div>"
		            + "</div>"
		            + "</body>"
		            + "</html>"; 
		 return htmlContent;
	}
}
