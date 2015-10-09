package com.businesskaro.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the tbl_policies database table.
 * 
 */
@Entity
@Table(name = "tbl_policies")
@NamedQuery(name = "TblPolicy.findAll", query = "SELECT t FROM TblPolicy t")
public class TblPolicy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "POLICY_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int policyId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DT")
	private Date createDt;

	@Column(name = "IMAGE_URL")
	private String imageUrl;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPD")
	private Date lastUpd;

	@Column(name = "POLICY_DESC")
	private String policyDesc;

	@Column(name = "POLICY_TITLE")
	private String policyTitle;

	@Column(name = "POLICY_TYPE")
	private String policyType;

	// bi-directional many-to-one association to BrgTopicsIndustry
	@OneToMany(mappedBy = "tblPolicy")
	private List<BrgTopicsIndustry> brgTopicsIndustries;

	// bi-directional many-to-one association to BrgTopicsIndustry
	@OneToMany(mappedBy = "tblPolicy")
	private List<BrgTopicsState> brgTopicsStates;

	public List<BrgTopicsState> getBrgTopicsStates() {
		return brgTopicsStates;
	}

	public void setBrgTopicsStates(List<BrgTopicsState> brgTopicsStates) {
		this.brgTopicsStates = brgTopicsStates;
	}

	public TblPolicy() {
	}

	public int getPolicyId() {
		return this.policyId;
	}

	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}

	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getLastUpd() {
		return this.lastUpd;
	}

	public void setLastUpd(Date lastUpd) {
		this.lastUpd = lastUpd;
	}

	public String getPolicyDesc() {
		return this.policyDesc;
	}

	public void setPolicyDesc(String policyDesc) {
		this.policyDesc = policyDesc;
	}

	public String getPolicyTitle() {
		return this.policyTitle;
	}

	public void setPolicyTitle(String policyTitle) {
		this.policyTitle = policyTitle;
	}

	public String getPolicyType() {
		return this.policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public List<BrgTopicsIndustry> getBrgTopicsIndustries() {
		return this.brgTopicsIndustries;
	}

	public void setBrgTopicsIndustries(
			List<BrgTopicsIndustry> brgTopicsIndustries) {
		this.brgTopicsIndustries = brgTopicsIndustries;
	}

	public BrgTopicsIndustry addBrgTopicsIndustry(
			BrgTopicsIndustry brgTopicsIndustry) {
		getBrgTopicsIndustries().add(brgTopicsIndustry);
		brgTopicsIndustry.setTblPolicy(this);

		return brgTopicsIndustry;
	}

	public BrgTopicsIndustry removeBrgTopicsIndustry(
			BrgTopicsIndustry brgTopicsIndustry) {
		getBrgTopicsIndustries().remove(brgTopicsIndustry);
		brgTopicsIndustry.setTblPolicy(null);

		return brgTopicsIndustry;
	}

}