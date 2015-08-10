package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the brg_usr_skills database table.
 * 
 */
@Entity
@Table(name="brg_usr_skills")
@NamedQuery(name="UserSkill.findAll", query="SELECT b FROM UserSkill b")
public class UserSkill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USR_SKIL_ID")
	private int usrSkilId;

	//bi-directional many-to-one association to TblUserPersInfoSumry
	@ManyToOne
	@JoinColumn(name="USR_ID")
	private UserPersonalInfoSummary tblUserPersInfoSumry;

	//bi-directional many-to-one association to LkpSkill
	@ManyToOne
	@JoinColumn(name="SKILL_ID")
	private LkpSkill lkpSkill;

	public UserSkill() {
	}

	public int getUsrSkilId() {
		return this.usrSkilId;
	}

	public void setUsrSkilId(int usrSkilId) {
		this.usrSkilId = usrSkilId;
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