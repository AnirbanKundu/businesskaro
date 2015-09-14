package com.businesskaro.model;

import java.util.Date;
import java.util.List;

public class OfferRequest {

	public Integer id;
	public String title;
	public String description;
	public int[] trgtIndustry;
	public int intdAudience;
	public int[] trgtLocation;
	public String imgUrl;
	public List<Questions> questionList;
	public int userId;
	public boolean isVerified = false;
	
	public Date createDate;
	public Date updateDate;
	
}
