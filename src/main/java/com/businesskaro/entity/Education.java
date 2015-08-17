package com.businesskaro.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the lkp_education database table.
 * 
 */
@Entity
@Table(name="lkp_education")
@NamedQuery(name="Education.findAll", query="SELECT l FROM Education l")
public class Education implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="EDUCTN_ID")
	private int eductnId;

	@Column(name="EDUCTN_DESCR")
	private String eductnDescr;

	@Column(name="EDUCTN_NAME")
	private String eductnName;

	//bi-directional many-to-one association to TblUserPersInfoDetail
	@JsonIgnore
	@OneToMany(mappedBy="lkpEducation")
	private List<UserPersonalInfoDetails> tblUserPersInfoDetails;

	public Education() {
	}

	public int getEductnId() {
		return this.eductnId;
	}

	public void setEductnId(int eductnId) {
		this.eductnId = eductnId;
	}

	public String getEductnDescr() {
		return this.eductnDescr;
	}

	public void setEductnDescr(String eductnDescr) {
		this.eductnDescr = eductnDescr;
	}

	public String getEductnName() {
		return this.eductnName;
	}

	public void setEductnName(String eductnName) {
		this.eductnName = eductnName;
	}

	public List<UserPersonalInfoDetails> getTblUserPersInfoDetails() {
		return this.tblUserPersInfoDetails;
	}

	public void setTblUserPersInfoDetails(List<UserPersonalInfoDetails> tblUserPersInfoDetails) {
		this.tblUserPersInfoDetails = tblUserPersInfoDetails;
	}

	public UserPersonalInfoDetails addTblUserPersInfoDetail(UserPersonalInfoDetails tblUserPersInfoDetail) {
		getTblUserPersInfoDetails().add(tblUserPersInfoDetail);
		tblUserPersInfoDetail.setLkpEducation(this);

		return tblUserPersInfoDetail;
	}

	public UserPersonalInfoDetails removeTblUserPersInfoDetail(UserPersonalInfoDetails tblUserPersInfoDetail) {
		getTblUserPersInfoDetails().remove(tblUserPersInfoDetail);
		tblUserPersInfoDetail.setLkpEducation(null);

		return tblUserPersInfoDetail;
	}

}