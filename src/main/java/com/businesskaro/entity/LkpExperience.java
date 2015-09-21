package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the lkp_experience database table.
 * 
 */
@Entity
@Table(name="lkp_experience")
@NamedQuery(name="LkpExperience.findAll", query="SELECT l FROM LkpExperience l")
public class LkpExperience implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="EXPERIENCE_ID")
	private int experienceId;

	@Column(name="EXPER_DISPL_VAL")
	private String experDisplVal;

	@Column(name="EXPER_MAX_VAL")
	private int experMaxVal;

	@Column(name="EXPER_MIN_VAL")
	private int experMinVal;

	//bi-directional many-to-one association to TblUserPersInfoDetail
	@JsonIgnore
	@OneToMany(mappedBy="lkpExperience")
	private List<UserPersonalInfoDetails> tblUserPersInfoDetails;

	public LkpExperience() {
	}

	public int getExperienceId() {
		return this.experienceId;
	}

	public void setExperienceId(int experienceId) {
		this.experienceId = experienceId;
	}

	public String getExperDisplVal() {
		return this.experDisplVal;
	}

	public void setExperDisplVal(String experDisplVal) {
		this.experDisplVal = experDisplVal;
	}

	public int getExperMaxVal() {
		return this.experMaxVal;
	}

	public void setExperMaxVal(int experMaxVal) {
		this.experMaxVal = experMaxVal;
	}

	public int getExperMinVal() {
		return this.experMinVal;
	}

	public void setExperMinVal(int experMinVal) {
		this.experMinVal = experMinVal;
	}

	public List<UserPersonalInfoDetails> getTblUserPersInfoDetails() {
		return this.tblUserPersInfoDetails;
	}

	public void setTblUserPersInfoDetails(List<UserPersonalInfoDetails> tblUserPersInfoDetails) {
		this.tblUserPersInfoDetails = tblUserPersInfoDetails;
	}

	public UserPersonalInfoDetails addTblUserPersInfoDetail(UserPersonalInfoDetails tblUserPersInfoDetail) {
		getTblUserPersInfoDetails().add(tblUserPersInfoDetail);
		tblUserPersInfoDetail.setLkpExperience(this);

		return tblUserPersInfoDetail;
	}

	public UserPersonalInfoDetails removeTblUserPersInfoDetail(UserPersonalInfoDetails tblUserPersInfoDetail) {
		getTblUserPersInfoDetails().remove(tblUserPersInfoDetail);
		tblUserPersInfoDetail.setLkpExperience(null);

		return tblUserPersInfoDetail;
	}

}