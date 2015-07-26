package com.businesskaro.model;

public enum StateEnum {
	
	ANDHRA_PRADESH("AP"), 
	WEST_BENGAL("WB");
	
	 StateEnum(String stateCode){
		this.stateCode = stateCode;
	}
	
	private String stateCode;
}
