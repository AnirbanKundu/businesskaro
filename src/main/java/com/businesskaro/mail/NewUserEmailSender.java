package com.businesskaro.mail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.model.EmailTo;

@RestController
public class NewUserEmailSender extends AbstractEmailNotification{
	

	@RequestMapping(value="/newUserMail", method = RequestMethod.POST)
    public void send(@RequestBody EmailTo email) throws Exception {   
		System.out.println("EMAIL: "+email.getEmail());
		send(email.getEmail());
    	
    }
	
	private String toAddress;
	private String subject;
	private Map<String, String> emailTokens;

	public void send(String toEmailAddress) throws Exception {
		this.toAddress = toEmailAddress;
		this.subject = "Welcome to Business Karo";
		loadEmailTokens();
		sendEmail("CREATE_USER_TEMPLATE.txt");
	}

	private void loadEmailTokens() {
		emailTokens = new HashMap<String, String>();
		emailTokens.put("#test_text", "Welcome to businesskaro.com. You have succesfully registered.");

	}

	@Override
	public Map<String, String> getEmailTokens() {
		return emailTokens;
	}

	@Override
	public String getToAddress() {
		return toAddress;
	}
	
	@Override
	public String getSubject(){
		return subject;
	}

}
