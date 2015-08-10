package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the lkp_age_grp database table.
 * 
 */
@Entity
@Table(name="lkp_age_grp")
@NamedQuery(name="AgeGroup.findAll", query="SELECT l FROM AgeGroup l")
public class AgeGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="AGE_ID")
	private int ageId;

	@Column(name="AGE_MAN")
	private int ageMan;

	@Column(name="AGE_MIN")
	private int ageMin;

	@Column(name="DISPL_AGE")
	private String displAge;

	//bi-directional many-to-one association to TblUserPersInfoDetail
	@OneToMany(mappedBy="lkpAgeGrp")
	private List<UserPersonalInfoDetails> tblUserPersInfoDetails;

	public AgeGroup() {
	}

	public int getAgeId() {
		return this.ageId;
	}

	public void setAgeId(int ageId) {
		this.ageId = ageId;
	}

	public int getAgeMan() {
		return this.ageMan;
	}

	public void setAgeMan(int ageMan) {
		this.ageMan = ageMan;
	}

	public int getAgeMin() {
		return this.ageMin;
	}

	public void setAgeMin(int ageMin) {
		this.ageMin = ageMin;
	}

	public String getDisplAge() {
		return this.displAge;
	}

	public void setDisplAge(String displAge) {
		this.displAge = displAge;
	}

	public List<UserPersonalInfoDetails> getTblUserPersInfoDetails() {
		return this.tblUserPersInfoDetails;
	}

	public void setTblUserPersInfoDetails(List<UserPersonalInfoDetails> tblUserPersInfoDetails) {
		this.tblUserPersInfoDetails = tblUserPersInfoDetails;
	}

	public UserPersonalInfoDetails addTblUserPersInfoDetail(UserPersonalInfoDetails tblUserPersInfoDetail) {
		getTblUserPersInfoDetails().add(tblUserPersInfoDetail);
		tblUserPersInfoDetail.setLkpAgeGrp(this);

		return tblUserPersInfoDetail;
	}

	public UserPersonalInfoDetails removeTblUserPersInfoDetail(UserPersonalInfoDetails tblUserPersInfoDetail) {
		getTblUserPersInfoDetails().remove(tblUserPersInfoDetail);
		tblUserPersInfoDetail.setLkpAgeGrp(null);

		return tblUserPersInfoDetail;
	}

}