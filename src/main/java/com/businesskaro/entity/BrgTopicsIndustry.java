package com.businesskaro.entity;

import java.io.Serializable;

import javax.persistence.*;




/**
 * The persistent class for the brg_topics_industry database table.
 * 
 */
@Entity
@Table(name="brg_topics_industry")
@NamedQuery(name="BrgTopicsIndustry.findAll", query="SELECT b FROM BrgTopicsIndustry b")
public class BrgTopicsIndustry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="POLICY_INDUS_ID")
	private int policyIndusId;

	//bi-directional many-to-one association to TblPolicy
	@ManyToOne
	@JoinColumn(name="POLICY_ID")
	private TblPolicy tblPolicy;

	//bi-directional many-to-one association to LkpIndustry
	@ManyToOne
	@JoinColumn(name="INDUSTRY_ID")
	private LkpIndustry lkpIndustry;

	public BrgTopicsIndustry() {
	}

	public int getPolicyIndusId() {
		return this.policyIndusId;
	}

	public void setPolicyIndusId(int policyIndusId) {
		this.policyIndusId = policyIndusId;
	}

	public TblPolicy getTblPolicy() {
		return this.tblPolicy;
	}

	public void setTblPolicy(TblPolicy tblPolicy) {
		this.tblPolicy = tblPolicy;
	}

	public LkpIndustry getLkpIndustry() {
		return this.lkpIndustry;
	}

	public void setLkpIndustry(LkpIndustry lkpIndustry) {
		this.lkpIndustry = lkpIndustry;
	}

}