package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the lkp_state database table.
 * 
 */
@Entity
@Table(name="lkp_state")
@NamedQuery(name="LkpState.findAll", query="SELECT l FROM LkpState l")
public class LkpState implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="STATE_ID")
	private int stateId;

	@Column(name="STATE_NAME")
	private String stateName;

	@Column(name="STATE_SHRT_NM")
	private String stateShrtNm;

	//bi-directional many-to-one association to BrgUsrReqrState@JsonIgnore
	@JsonIgnore
	@OneToMany(mappedBy="lkpState")
	private List<BrgUsrReqrState> brgUsrReqrStates;

	//bi-directional many-to-one association to TblUserPersInfoDetail
	@JsonIgnore
	@OneToMany(mappedBy="lkpState")
	private List<UserPersonalInfoDetails> tblUserPersInfoDetails;

	public LkpState() {
	}

	public int getStateId() {
		return this.stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateShrtNm() {
		return this.stateShrtNm;
	}

	public void setStateShrtNm(String stateShrtNm) {
		this.stateShrtNm = stateShrtNm;
	}

	public List<BrgUsrReqrState> getBrgUsrReqrStates() {
		return this.brgUsrReqrStates;
	}

	public void setBrgUsrReqrStates(List<BrgUsrReqrState> brgUsrReqrStates) {
		this.brgUsrReqrStates = brgUsrReqrStates;
	}

	public BrgUsrReqrState addBrgUsrReqrState(BrgUsrReqrState brgUsrReqrState) {
		getBrgUsrReqrStates().add(brgUsrReqrState);
		brgUsrReqrState.setLkpState(this);

		return brgUsrReqrState;
	}

	public BrgUsrReqrState removeBrgUsrReqrState(BrgUsrReqrState brgUsrReqrState) {
		getBrgUsrReqrStates().remove(brgUsrReqrState);
		brgUsrReqrState.setLkpState(null);

		return brgUsrReqrState;
	}

	public List<UserPersonalInfoDetails> getTblUserPersInfoDetails() {
		return this.tblUserPersInfoDetails;
	}

	public void setTblUserPersInfoDetails(List<UserPersonalInfoDetails> tblUserPersInfoDetails) {
		this.tblUserPersInfoDetails = tblUserPersInfoDetails;
	}

	public UserPersonalInfoDetails addTblUserPersInfoDetail(UserPersonalInfoDetails tblUserPersInfoDetail) {
		getTblUserPersInfoDetails().add(tblUserPersInfoDetail);
		tblUserPersInfoDetail.setLkpState(this);

		return tblUserPersInfoDetail;
	}

	public UserPersonalInfoDetails removeTblUserPersInfoDetail(UserPersonalInfoDetails tblUserPersInfoDetail) {
		getTblUserPersInfoDetails().remove(tblUserPersInfoDetail);
		tblUserPersInfoDetail.setLkpState(null);

		return tblUserPersInfoDetail;
	}

}