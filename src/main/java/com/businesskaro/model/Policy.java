package com.businesskaro.model;

import java.util.Date;
public class Policy {
	
	public Integer id;
	public String policyDesc;
	public String policyTitle;
	public String policyType;
	public String imageUrl;
	public int[]  industrys;
	public int[]  states;	
	public int isFeatured=0;
	public int userId;	
	public Date createDate;
	public Date updateDate;
	
	public String industryName;
	public String stateName; 
}
