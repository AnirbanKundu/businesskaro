package com.businesskaro.mail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class NewUserEmailSender extends AbstractEmailNotification{
	
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