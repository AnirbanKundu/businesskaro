package com.businesskaro.model;

import java.util.Date;

public class BKUserProfileDetails{
	public int userId;
	public int ageGroupId;
	public int stateId;
	public int educatonId;
	public int experienceId;
	public int professionalId;
	public String faceBookUrl;
	public String linkedInUrl;
	public String imageUrl;
	public String twiterURL;
	public Date updatedDate;
	public Date createdDate;

	@Override
	public String toString() {
		return "BKUserProfileDetails [userId=" + userId + ", ageGroupId="
				+ ageGroupId + ", stateId=" + stateId + ", educatonId="
				+ educatonId + ", experienceId=" + experienceId
				+ ", professionalId=" + professionalId + ", faceBookUrl="
				+ faceBookUrl + ", linkedInUrl=" + linkedInUrl + ", imageUrl="
				+ imageUrl + ", twiterURL=" + twiterURL + ", updatedDate="
				+ updatedDate + ", createdDate=" + createdDate + "]";
	}
	
	
}
