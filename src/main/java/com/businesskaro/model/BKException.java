package com.businesskaro.model;

public class BKException extends RuntimeException{

	public String errorMsg;
	
	public String errorCode;
	
	public enum Type {
		IN_VALID_USER,
		INTERNAL_ERRROR,
		BUSSINESS_VALIDATION,
		USER_AUTH_FAIL
	};
	
	public Type errorType;
	
	public BKException(String msg, String code, Type t){
		errorMsg = msg;
		errorCode = code;
		errorType = t;
	}
	
}
