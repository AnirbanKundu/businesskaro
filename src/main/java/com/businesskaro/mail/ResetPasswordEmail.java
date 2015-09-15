package com.businesskaro.mail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ResetPasswordEmail extends AbstractEmailNotification{

	private String toAddress;
	private String subject;
	private Map<String, String> emailTokens;
	
	public void send(String toEmailAddress, String tempPassword) throws Exception {
		this.toAddress = toEmailAddress;
		this.subject = "[BusinessKaro] Please reset your password";
		emailTokens = new HashMap<String, String>();
		emailTokens.put("#tempPassword", tempPassword);
		sendEmail("RESET_PASSWORD_TEMPLATE.txt");
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
