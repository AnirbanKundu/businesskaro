package com.businesskaro.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.mail.Mail;
import com.businesskaro.model.EMailMessage;

@RestController
class MailController {
    //private final JavaMailSender javaMailSender; 
	
    @Autowired
	Mail email;
	

    // JUST FOR TESTING.THE EMAIL ROUTE NOT AVAILBLE TO EXT WORLD
    @RequestMapping(value="/mail", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void send(@RequestBody EMailMessage mailMessage) throws Exception {   
    	/*
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("durgaprasadkumar@gmail.com");
        mailMessage.setReplyTo("shalya.sighal@gmail.com");
        mailMessage.setFrom("consultingguru21@gmail.com");
        mailMessage.setSubject("From BK");
        mailMessage.setText("Durga main tereko kha jaunga.. ha ha ha :-)");
        javaMailSender.send(mailMessage);
        return mailMessage;
        */    	
    	//email.sendMail("Anirban","Kundu","123");
    	email.sendMail(mailMessage);
    	
    }
}
