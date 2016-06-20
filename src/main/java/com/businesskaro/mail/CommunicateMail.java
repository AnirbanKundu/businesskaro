package com.businesskaro.mail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.businesskaro.model.Communicate;
import com.businesskaro.model.ContactUs;





@Service
public class CommunicateMail extends AbstractEmailNotification{

	private String toAddress;
	private String fromAddress;
	private String subject;
	private Map<String, String> emailTokens;
	
	@Autowired 
	private ApplicationContext ctx;	
	
	public void contactUs(ContactUs contactus) throws Exception{
		this.toAddress = ctx.getEnvironment().getProperty("mail_contactus_to");
		this.fromAddress=contactus.email;
		this.subject = contactus.subject;
		loadContactUsTokens(contactus);
		sendEmail("CONTACT_US_TEMPLATE.txt");
	}
	
	private void loadContactUsTokens(ContactUs contactus) {
		emailTokens = new HashMap<String, String>();
		emailTokens.put("#from_Name", contactus.name);
		emailTokens.put("#from_Email", contactus.email);
		emailTokens.put("#message", contactus.message);
		emailTokens.put("#subject", contactus.subject);
		emailTokens.put("#mobile", contactus.mobileNo);
	}

	public void send(Communicate communicate) throws Exception {
		this.toAddress = communicate.toEmailAddress;
		this.fromAddress = communicate.fromEmailAddress;
		this.subject = "Welcome to Business Karo";
		loadEmailTokens(communicate);
		sendEmail("CONTACT_USER_TEMPLATE.txt");
	}

	private void loadEmailTokens(Communicate communicate) {
		emailTokens = new HashMap<String, String>();
		emailTokens.put("#to_Name", communicate.toName);
		emailTokens.put("#from_Name", communicate.fromName);
		emailTokens.put("#message", communicate.message);
		emailTokens.put("#entity_type", communicate.entityType);
		emailTokens.put("#entity_id", communicate.entityId);
		//emailTokens.put("#server", "https://businesskaro.in");
		emailTokens.put("#server", "http://www.businesskaro.in");
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
	public String getSubject() {
		return subject;
	}

	@Override
	public String getFromAddress() {
		return fromAddress;
	}

}
