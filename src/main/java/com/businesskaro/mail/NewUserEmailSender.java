package com.businesskaro.mail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class NewUserEmailSender extends AbstractEmailNotification{
	
	private String toAddress;
	private String subject;
	private String fromAddress;
	private Map<String, String> emailTokens;

	public void send(String toEmailAddress) throws Exception {
		this.toAddress = toEmailAddress;
		this.subject = "Welcome to Business Karo";
		this.fromAddress = "admin@businesskaro.com";
		loadEmailTokens();
		sendEmail("CREATE_USER_TEMPLATE.txt");
	}
	
	/* Added by Nagendra 1-Mar-2016 */
	public void sendEmailAndURL(String toEmailAddress,String url) throws Exception {
		
		this.toAddress = toEmailAddress;
		this.subject = "Welcome to Business Karo";
		this.fromAddress = "admin@businesskaro.com";
		
		loadEmailTokens(url);
		//registrationLink();
		sendEmail("CREATE_USER_TEMPLATE.txt");
	}
	private void loadEmailTokens(String url) {
		emailTokens = new HashMap<String, String>();
		emailTokens.put("#test_text", "Welcome to businesskaro.com. You have succesfully registered.");		
		emailTokens.put("#activatation_link", "Please click the URL to login to the application: "+url);
	}
	/* Added by Nagendra */
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
	
	@Override
	public String getFromAddress() {
		return fromAddress;
	}

}
