package com.businesskaro.model;

import java.util.Date;
import java.util.List;

public class OfferRequest {

	public String title;
	public String compName;
	public String description;
	public int[] trgtIndustry;
	public int[] intdAudience;
	public List<String> trgtLocation;
	public String imgUrl;
	public List<Questions> questionList;
	
	public Date createDate;
	public Date updateDate;
	
}
