package com.techlabs.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import jakarta.mail.MessagingException;

@SpringBootApplication
public class Application {

	@Autowired
	private EmailSenderService emailSenderService;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void triggerMail() throws MessagingException {
		emailSenderService.sendEmailWithAttachment("suyashawasthy2001@gmail.com", "This is email body.", "This is email with attachment.", "/Users/stuti/Desktop/20240702_140109.jpg");
	}

}
