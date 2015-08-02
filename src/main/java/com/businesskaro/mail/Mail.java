package com.businesskaro.mail;

import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.businesskaro.model.EMailMessage;
import com.businesskaro.model.EmailTo;

/*
 import javax.mail.Message;
 import javax.mail.MessagingException;
 import javax.mail.Session;
 import javax.mail.Transport;
 import javax.mail.internet.InternetAddress;
 import javax.mail.internet.MimeMessage;
 */
@Component
public class Mail {

	private static Logger log = LoggerFactory.getLogger(Mail.class);

	private final JavaMailSender javaMailSender;

	@Autowired
	public Mail(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendMailx(String from, String to, String body) {
		/*
		 * String host = "localhost"; Properties properties =
		 * System.getProperties(); properties.setProperty("mail.smtp.host",
		 * host); Session session = Session.getDefaultInstance(properties); try
		 * { MimeMessage message = new MimeMessage(session); message.setFrom(new
		 * InternetAddress(from));
		 * message.addRecipient(Message.RecipientType.TO, new
		 * InternetAddress(to)); message.setText(body); Transport.send(message);
		 * } catch (MessagingException mex) { mex.printStackTrace(); }
		 */

	}
	// TBD ---- If User details needs to be logged we need to pass the User object as well .
	public void sendMail(EMailMessage mailMessage) throws Exception {
		
		MimeMessage msg = javaMailSender.createMimeMessage();
		try {
				msg.setFrom(new InternetAddress(mailMessage.getFrom_email(),mailMessage.getFrom_name()));
				
	            for (EmailTo to : mailMessage.getTos() ) {           	 
	           		 msg.addRecipient(RecipientType.TO, new InternetAddress(to.getEmail()));           	 
	            }
	            msg.setSubject(mailMessage.getSubject());    
	            // msg.setHeader(h.getKey(), h.getValue());
	            msg.setText(mailMessage.getHtml());
	            javaMailSender.send(msg);
	            // TBD --- Log the User details ----- .
	             
		} catch (Exception ex) {
			throw ex;
		}
		

	}
}
