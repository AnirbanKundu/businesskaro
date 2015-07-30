package com.businesskaro.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Main email message class
 */
public class EMailMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty
	private String text;
	@JsonProperty
	private String subject;
	@JsonProperty
	private String from_email;
	@JsonProperty
	private String from_name;
	@JsonProperty
	private List<EmailTo> tos = new ArrayList<EmailTo>();
	@JsonProperty
	private Boolean important;
	
	@JsonProperty
	private String html;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getFrom_email() {
		return from_email;
	}
	public void setFrom_email(String from_email) {
		this.from_email = from_email;
	}
	public String getFrom_name() {
		return from_name;
	}
	public void setFrom_name(String from_name) {
		this.from_name = from_name;
	}
	public List<EmailTo> getTos() {
		return tos;
	}
	public void setTos(List<EmailTo> tos) {
		this.tos = tos;
	}
	public Boolean getImportant() {
		return important;
	}
	public void setImportant(Boolean important) {
		this.important = important;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}

}
