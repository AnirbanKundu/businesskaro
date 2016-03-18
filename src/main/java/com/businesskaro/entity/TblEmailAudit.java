package com.businesskaro.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_email_audit_trial")
public class TblEmailAudit implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="email_audit_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int emailAuditId;

	@Column(name="entity_id")
	private int entityId;

	@Column(name="entity_type")
	private String entityType;

	@Column(name="sent_from")
	private int sentFrom;
	
	@Column(name="sent_to")
	private int sentTo;
	
	@Column(name="email_sent_date")
	private Date emailSentDate;
	
	@Column(name = "email_content")
	private String emailContent;
	
	@Column(name = "email_subject")
	private String emailSubject;
	
	@Column(name = "sent_to_email")
	private String sentToEmail;
	
	@Column(name = "sent_from_email")
	private String sentFromEmail;

	public int getEmailAuditId() {
		return emailAuditId;
	}

	public void setEmailAuditId(int emailAuditId) {
		this.emailAuditId = emailAuditId;
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public int getSentFrom() {
		return sentFrom;
	}

	public void setSentFrom(int sentFrom) {
		this.sentFrom = sentFrom;
	}

	public int getSentTo() {
		return sentTo;
	}

	public void setSentTo(int sentTo) {
		this.sentTo = sentTo;
	}

	public Date getEmailSentDate() {
		return emailSentDate;
	}

	public void setEmailSentDate(Date emailSentDate) {
		this.emailSentDate = emailSentDate;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getSentToEmail() {
		return sentToEmail;
	}

	public void setSentToEmail(String sentToEmail) {
		this.sentToEmail = sentToEmail;
	}

	public String getSentFromEmail() {
		return sentFromEmail;
	}

	public void setSentFromEmail(String sentFromEmail) {
		this.sentFromEmail = sentFromEmail;
	}
	
	
	
}
