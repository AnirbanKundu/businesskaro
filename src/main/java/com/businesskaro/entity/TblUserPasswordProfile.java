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

public class TblUserPasswordProfile implements Serializable {
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

	@Column(name="PROFILE_CREATED")
	private int profileCreated;

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