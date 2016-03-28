/*
 * Author: Nagendra 
 * Created Date: 23-Feb-2016
 */



package com.businesskaro.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tbl_unregistered_user_password")

@NamedQueries({
	//@NamedQuery(name="TblUnregisteredUserPassword.findAll", query="SELECT t FROM TblUnregisteredUserPassword t"),
	//@NamedQuery(name="Country.findByName",query="SELECT c FROM Country c WHERE c.name = :name"),
	//@NamedQuery(name="TblUnregisteredUserPassword.findAll", query="t FROM TblUnregisteredUserPassword t"),
	//@NamedQuery(name="TblUnregisteredUserPassword.findByUsrSalt", query="DELETE FROM TblUnregisteredUserPassword t where t.usr_salt=?"),
	
	
	//@NamedQuery(name="TblUnregisteredUserPassword.findByName", query = "SELECT p FROM TblUnregisteredUserPassword p WHERE p.usr_salt = ?1")
	//@NamedQuery(query = "Select e from TblUnregisteredUserPassword e where e.usr_salt = :userSalt", name = "queryUserSalt")
})
public class TblUnregisteredUserPassword implements Serializable{
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

	public int getProfileCreated() {
		return profileCreated;
	}

	public void setProfileCreated(int profileCreated) {
		this.profileCreated = profileCreated;
	}

	public TblUnregisteredUserPassword() {
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
