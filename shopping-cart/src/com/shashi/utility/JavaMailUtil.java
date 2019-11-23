package com.shashi.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailUtil {
	public  static void sendMail(String recipientMailId) throws MessagingException {
		
		System.out.println("Preparing to send Mail");
		Properties properties = new Properties();
		String host = "smtp.gmail.com";
		properties.put("mail.smtp.host", host);
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", "587");
		
		Connection con = DBUtil.provideConnection();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement("select * from admin");
			
			rs = ps.executeQuery();
			if(rs.next()) {
			String emailId = rs.getString("email");
			String passWord = rs.getString("password");
			
			properties.put("mail.user", emailId);
			properties.put("mail.password", passWord);
			
			Session session = Session.getInstance(properties, new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(emailId,passWord);
				}
				
			});
			
			Message message = prepareMessage(session,emailId,recipientMailId);
			
			Transport.send(message);
			
			System.out.println("Message Sent Successfully!");
			}
		} catch (SQLException e) {
			System.out.println("Message Sending Failed\n Error: "+e);
			e.printStackTrace();
		}
		
		
		
	}
	
	
	private static Message prepareMessage(Session session,String myAccountEmail, String recipientEmail) {
		
		try {
			
			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
			message.setSubject("Welcome to Ellison Electronics");
			message.setText("Hey! "+recipientEmail + ", Thanks  for Signing Up with us!");
			return message;
			
		}
		catch(Exception exception) {
			Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE,null,exception);
		}
		return null;
		
	}
	
	
	protected static void sendMail(String recipient,String subject, String htmlTextMessage) throws MessagingException {
		
		System.out.println("Preparing to send Mail");
		Properties properties = new Properties();
		String host = "smtp.gmail.com";
		properties.put("mail.smtp.host", host);
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", "587");
		
		Connection con = DBUtil.provideConnection();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement("select * from admin");
			
			rs = ps.executeQuery();
			if(rs.next()) {
			String emailId = rs.getString("email");
			String passWord = rs.getString("password");
			
			properties.put("mail.user", emailId);
			properties.put("mail.password", passWord);
			
			Session session = Session.getInstance(properties, new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(emailId,passWord);
				}
				
			});
			
			Message message = prepareMessage(session,emailId,recipient,subject,htmlTextMessage);
			
			Transport.send(message);
			
			System.out.println("Message Sent Successfully!");
			}
		} catch (SQLException e) {
			System.out.println("Message Sending Failed\n Error: "+e);
			e.printStackTrace();
		}
		
		
	}
	
	private static Message prepareMessage(Session session,String myAccountEmail, String recipientEmail, String subject , String htmlTextMessage) {
		
		try {
			
			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
			message.setSubject(subject);
			message.setContent(htmlTextMessage, "text/html");
			return message;
			
		}
		catch(Exception exception) {
			Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE,null,exception);
		}
		return null;
		
	}
}
