package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the brg_usr_industry database table.
 * 
 */
@Entity
@Table(name="brg_usr_industry")
@NamedQuery(name="BrgUsrIndustry.findAll", query="SELECT b FROM BrgUsrIndustry b")
public class BrgUsrIndustry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USR_INDUS_ID")
	private int usrIndusId;

	//bi-directional many-to-one association to TblUserPersInfoSumry
	@ManyToOne
	@JoinColumn(name="USR_ID")
	private UserPersonalInfoSummary tblUserPersInfoSumry;

	//bi-directional many-to-one association to LkpIndustry
	@ManyToOne
	@JoinColumn(name="INDUSTRY_ID")
	private LkpIndustry lkpIndustry;

	public BrgUsrIndustry() {
	}

	public int getUsrIndusId() {
		return this.usrIndusId;
	}

	public void setUsrIndusId(int usrIndusId) {
		this.usrIndusId = usrIndusId;
	}

	public UserPersonalInfoSummary getTblUserPersInfoSumry() {
		return this.tblUserPersInfoSumry;
	}

	public void setTblUserPersInfoSumry(UserPersonalInfoSummary tblUserPersInfoSumry) {
		this.tblUserPersInfoSumry = tblUserPersInfoSumry;
	}

	public LkpIndustry getLkpIndustry() {
		return this.lkpIndustry;
	}

	public void setLkpIndustry(LkpIndustry lkpIndustry) {
		this.lkpIndustry = lkpIndustry;
	}

}