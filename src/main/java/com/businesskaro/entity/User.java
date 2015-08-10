package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_name")
	private String userName;

	private String email;

	@Column(name="password_enc")
	private String passwordEnc;

	private String phone;

	public User() {
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordEnc() {
		return this.passwordEnc;
	}

	public void setPasswordEnc(String passwordEnc) {
		this.passwordEnc = passwordEnc;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}