package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the brg_usr_looking_for database table.
 * 
 */
@Entity
@Table(name="brg_usr_looking_for")
@NamedQuery(name="BrgUsrLookingFor.findAll", query="SELECT b FROM BrgUsrLookingFor b")
public class BrgUsrLookingFor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USR_SKIL_LKG_ID")
	private int usrSkilLkgId;

	//bi-directional many-to-one association to TblUserPersInfoSumry
	@ManyToOne
	@JoinColumn(name="USR_ID")
	private UserPersonalInfoSummary tblUserPersInfoSumry;

	//bi-directional many-to-one association to LkpSkill
	@ManyToOne
	@JoinColumn(name="SKILL_ID")
	private LkpSkill lkpSkill;

	public BrgUsrLookingFor() {
	}

	public int getUsrSkilLkgId() {
		return this.usrSkilLkgId;
	}

	public void setUsrSkilLkgId(int usrSkilLkgId) {
		this.usrSkilLkgId = usrSkilLkgId;
	}

	public UserPersonalInfoSummary getTblUserPersInfoSumry() {
		return this.tblUserPersInfoSumry;
	}

	public void setTblUserPersInfoSumry(UserPersonalInfoSummary tblUserPersInfoSumry) {
		this.tblUserPersInfoSumry = tblUserPersInfoSumry;
	}

	public LkpSkill getLkpSkill() {
		return this.lkpSkill;
	}

	public void setLkpSkill(LkpSkill lkpSkill) {
		this.lkpSkill = lkpSkill;
	}

}