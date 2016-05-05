package com.businesskaro.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;

@SuppressWarnings("unused")
public abstract class AbstractEmailNotification implements ResourceLoaderAware {

	@Autowired
	JavaMailSender javaMailSender;

	private ResourceLoader resourceLoader;

	private String emailSubject;

	public abstract Map<String, String> getEmailTokens();

	public abstract String getToAddress();
	public abstract String getFromAddress();

	public abstract String getSubject();

	public void sendEmail(String templateName) throws Exception {

		String body = readEmailTemplateAndReplaceTokens(templateName);
		String toAddress = getToAddress();

		MimeMessage msg = javaMailSender.createMimeMessage();
		try {

			msg.setFrom(new InternetAddress(getFromAddress()));

			msg.addRecipient(RecipientType.TO, new InternetAddress(toAddress));
			msg.setSubject(getSubject());

			MimeMultipart multipart = new MimeMultipart();
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(body.toString(), "text/html; charset=utf-8");
			multipart.addBodyPart(bodyPart);

			msg.setContent(multipart);
			javaMailSender.send(msg);

		} catch (Exception ex) { 
			throw ex;
		}
	}

	private String replaceTokens(StringBuffer buffer, Map<String, String> tokens) {
		String s = buffer.toString();
		for (Entry<String, String> mapEntry : tokens.entrySet()) {
			if (mapEntry.getValue() != null) {
				s = s.replaceAll(mapEntry.getKey(), mapEntry.getValue());
			} else {
				s = s.replaceAll(mapEntry.getKey(), "");
			}
		}
		return s;
	}

	private String readEmailTemplateAndReplaceTokens(String fileName) {
		StringBuffer buffer = null;
		try {
			buffer = getEmailTemplate(fileName);
		} catch (IOException e) {
			e.printStackTrace();
			return "Couldn't find email template.";
		}
		Map<String, String> tokens = getEmailTokens();
		return replaceTokens(buffer, tokens);
	}

	private StringBuffer getEmailTemplate(String templateName) throws IOException {

		InputStream ios = this.getClass().getClassLoader().getResourceAsStream(templateName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(ios));
		StringBuffer buffer = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		return buffer;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

}
