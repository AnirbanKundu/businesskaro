package com.businesskaro.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the tbl_user_password database table.
 *     @NamedQuery(name="Country.findByName",query="SELECT c FROM Country c WHERE c.name = :name"),
 */
@Entity
@Table(name="tbl_user_password")

@NamedQueries({
	@NamedQuery(name="TblUserPassword.findAll", query="SELECT t FROM TblUserPassword t")	
}) 

public class TblUserPassword implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USR_ID")
	private int usrId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_DT")
	private Date createDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_UPD")
	private Date lastUpd;

	@Column(name="USR_EMAIL")
	private String usrEmail;

	@Column(name="USR_NAME")
	private String usrName;

	@Column(name="USR_PASSWORD")
	private String usrPassword;

	@Column(name="USR_SALT")
	private String usrSalt;
	
	@Column(name="PROFILE_CREATED")
	private int profileCreated;
	
	/*
	@Column(name="USR_TYPE")
	private String userType;
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	*/

	public int getProfileCreated() {
		return profileCreated;
	}

	public void setProfileCreated(int profileCreated) {
		this.profileCreated = profileCreated;
	}

	public TblUserPassword() {
	}

	public int getUsrId() {
		return this.usrId;
	}

	public void setUsrId(int usrId) {
		this.usrId = usrId;
	}

	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Date getLastUpd() {
		return this.lastUpd;
	}

	public void setLastUpd(Date lastUpd) {
		this.lastUpd = lastUpd;
	}

	public String getUsrEmail() {
		return this.usrEmail;
	}

	public void setUsrEmail(String usrEmail) {
		this.usrEmail = usrEmail;
	}

	public String getUsrName() {
		return this.usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getUsrPassword() {
		return this.usrPassword;
	}

	public void setUsrPassword(String usrPassword) {
		this.usrPassword = usrPassword;
	}

	public String getUsrSalt() {
		return this.usrSalt;
	}

	public void setUsrSalt(String usrSalt) {
		this.usrSalt = usrSalt;
	}

}