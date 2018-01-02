package com.boilerplate.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;  

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;



public class SendMail {

	
	private final  JavaMailSender  mailSender  ;
	


	public SendMail( JavaMailSender mailSender) {
		this.mailSender = mailSender;
	

	}
	


	/**
	 * This method will send compose and send the message
	 * @throws MessagingException 
	 */
	public void sendMail(String to,  String msg) throws MessagingException {
		
		try{
		
	
			   
		MimeMessage message =  mailSender.createMimeMessage();  
		
		
        MimeMessageHelper helper = new MimeMessageHelper(message, true);  
         	
        helper.setFrom("testmailtestml@gmail.com");  
       
    
        
        helper.setTo(to);  
        helper.setSubject("PayTime");  
       
        helper.setText("text/html; charset=utf-8", msg);  
   
        mailSender.send(message);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
}
