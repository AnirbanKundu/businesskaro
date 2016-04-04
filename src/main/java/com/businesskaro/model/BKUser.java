package com.businesskaro.model;

public class BKUser {

	public int id;
	public String userName;
	public String password;
	public String email;
	public String phoneNo;
	public String randomSalt;
	public String userType;
	
	
	@Override
	public String toString() {
		return "BKUser [id=" + id + ", userName=" + userName + ", password="
				+ password + ", email=" + email + ", phoneNo=" + phoneNo + "]";
	}
	
	
	
}
