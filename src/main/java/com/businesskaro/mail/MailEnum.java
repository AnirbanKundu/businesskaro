package com.businesskaro.mail;

public enum MailEnum {

	LOGIN("LOGIN_TEMPLATE"),
	CREATE_USER("CREATE_USER_TEMPLATE");
	
	String value;
	MailEnum(String val )
	{
		this.value = val;
	}
	
	public String getTemplateName(){
		return value;
	}
}
