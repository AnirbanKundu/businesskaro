package com.businesskaro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.dao.BKUserDao;
import com.businesskaro.mail.Mail;

@RestController
@EnableAutoConfiguration
@RequestMapping("/sendmail")
public class SimpleRest {


	/*
	@Autowired
	Mail email;
	*/
	
	//the below annotation provides routing information.
	@RequestMapping(method = RequestMethod.GET)
    public String sendMail() {
		String to = "anirban.kundu1981@gmail.com";
		String from = "mengjiritesh@gmail.com";
		//email.sendMailx(from,to,"Test from business karo ");
        return "Mail sent";
    }
}
