package com.smart_devices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.smart_devices.model.MailSender;

@Service
public class MailService {
		@Autowired
	    private JavaMailSender javaMailSender;


	    @Value("${spring.mail.username}")
	    private String from;


	    public void sendMail(MailSender mailSender){
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom(from);
	        message.setTo(mailSender.to());
	        message.setSubject(mailSender.subject());
	        message.setText(mailSender.content());

	        javaMailSender.send(message);
	    }
}
