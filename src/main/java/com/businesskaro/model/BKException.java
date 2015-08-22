package com.businesskaro.model;

public class BKException extends RuntimeException{

	public String errorMsg;
	
	public String errorCode;
	
	public enum Type {
		IN_VALID_USER,
		INTERNAL_ERRROR,
		BUSSINESS_VALIDATION,
		USER_AUTH_FAIL,
		ENTITY_NOT_FOUND,
		USER_ALREADY_EXIST
	};
	
	public Type errorType;
	
	public BKException(String msg, String code, Type t){
		errorMsg = msg;
		errorCode = code;
		errorType = t;
	}

	@Override
	public String toString() {
		return "BKException [errorMsg=" + errorMsg + ", errorCode=" + errorCode
				+ ", errorType=" + errorType + "]";
	}
	
	
	 
	
}
