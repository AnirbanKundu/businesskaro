package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the lkp_profession database table.
 * 
 */
@Entity
@Table(name="lkp_profession")
@NamedQuery(name="LkpProfession.findAll", query="SELECT l FROM LkpProfession l")
public class LkpProfession implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PROFSION_ID")
	private int profsionId;

	@Column(name="PROFSION_NAME")
	private String profsionName;

	//bi-directional many-to-one association to TblUserPersInfoDetail
	@OneToMany(mappedBy="lkpProfession")
	private List<UserPersonalInfoDetails> tblUserPersInfoDetails;

	public LkpProfession() {
	}

	public int getProfsionId() {
		return this.profsionId;
	}

	public void setProfsionId(int profsionId) {
		this.profsionId = profsionId;
	}

	public String getProfsionName() {
		return this.profsionName;
	}

	public void setProfsionName(String profsionName) {
		this.profsionName = profsionName;
	}

	public List<UserPersonalInfoDetails> getTblUserPersInfoDetails() {
		return this.tblUserPersInfoDetails;
	}

	public void setTblUserPersInfoDetails(List<UserPersonalInfoDetails> tblUserPersInfoDetails) {
		this.tblUserPersInfoDetails = tblUserPersInfoDetails;
	}

	public UserPersonalInfoDetails addTblUserPersInfoDetail(UserPersonalInfoDetails tblUserPersInfoDetail) {
		getTblUserPersInfoDetails().add(tblUserPersInfoDetail);
		tblUserPersInfoDetail.setLkpProfession(this);

		return tblUserPersInfoDetail;
	}

	public UserPersonalInfoDetails removeTblUserPersInfoDetail(UserPersonalInfoDetails tblUserPersInfoDetail) {
		getTblUserPersInfoDetails().remove(tblUserPersInfoDetail);
		tblUserPersInfoDetail.setLkpProfession(null);

		return tblUserPersInfoDetail;
	}

}