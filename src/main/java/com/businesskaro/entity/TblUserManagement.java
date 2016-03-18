package com.businesskaro.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tbl_user_password")
@NamedQuery(name="TblUserManagement.findAll", query="SELECT l FROM TblUserManagement l")
public class TblUserManagement implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
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
    
    @Column(name="PROFILE_CREATED")
    private int profileCreated;
    
    @Column(name="USR_TYPE" ,columnDefinition="Varchar(50) default 'USER'")
	private String usrType;

    public String getUsrType() {
		return usrType;
	}

	public void setUsrType(String usrType) {
		this.usrType = usrType;
	}
	public String getUsrEmail() {
		return usrEmail;
	}

	public void setUsrEmail(String usrEmail) {
		this.usrEmail = usrEmail;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

    public int getUsrId() {
		return usrId;
	}

	public void setUsrId(int usrId) {
		this.usrId = usrId;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Date getLastUpd() {
		return lastUpd;
	}

	public void setLastUpd(Date lastUpd) {
		this.lastUpd = lastUpd;
	}

	public int getProfileCreated() {
		return profileCreated;
	}

	public void setProfileCreated(int profileCreated) {
		this.profileCreated = profileCreated;
	}
}
