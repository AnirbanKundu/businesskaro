package com.businesskaro.model;

import java.util.Date;
import java.util.Set;

public class BKUserProfileSummary{
	
	public int userId;
	public String firstName;
	public String lastName;
	public String stateName;
	public String cityName;
	public String aboutMe;
	public String userType;
	public String companyUrl;
	public String imageUrl;
	
	public Set<Integer> userSkills;
	public Set<Integer> industrys;
	public Set<Integer> lookinfForSkill;
	public Set<Integer> offeredServices;
	
	public Date updatedDate;
	public Date createdDate;
}
