package com.businesskaro.model;


import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailTo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty
	private String email;
	@JsonProperty
	private String recipientName;
	@JsonProperty
	private String type;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
}
