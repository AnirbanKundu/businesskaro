package com.businesskaro.model;

import java.util.Date;

public class AdminEntitySearch {
	public Integer entityid;
	public String entitytitle;
	public String entitydescription;
	public boolean isVerified = false;	
	public Date createDate;
	public Date updateDate;	
	public Integer usercreatedId;
	
	@Override
	public String toString() {
		return "AdminEntitySearch [entityid=" + entityid + ", entitytitle="
				+ entitytitle + ", entitydescription=" + entitydescription
				+ ", isVerified=" + isVerified + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + ", usercreatedId="
				+ usercreatedId + "]";
	} 
	
	
}

