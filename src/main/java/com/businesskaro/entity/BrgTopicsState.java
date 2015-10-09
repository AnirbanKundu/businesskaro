package com.businesskaro.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the brg_topics_state database table.
 * 
 */
@Entity
@Table(name="brg_topics_state")
@NamedQuery(name="BrgTopicsState.findAll", query="SELECT b FROM BrgTopicsState b")
public class BrgTopicsState implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="POLICY_STATE_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int policyStateId;

	//bi-directional many-to-one association to TblPolicy
	@ManyToOne
	@JoinColumn(name="POLICY_ID")
	private TblPolicy tblPolicy;

	//bi-directional many-to-one association to LkpIndustry
	@ManyToOne
	@JoinColumn(name="STATE_ID")
	private LkpState lkpState;

	public int getPolicyStateId() {
		return policyStateId;
	}

	public void setPolicyStateId(int policyStateId) {
		this.policyStateId = policyStateId;
	}

	public TblPolicy getTblPolicy() {
		return tblPolicy;
	}

	public void setTblPolicy(TblPolicy tblPolicy) {
		this.tblPolicy = tblPolicy;
	}

	public LkpState getLkpState() {
		return lkpState;
	}

	public void setLkpState(LkpState lkpState) {
		this.lkpState = lkpState;
	}
	
	

}