package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the lkp_industry database table.
 * 
 */
@Entity
@Table(name="lkp_industry")
@NamedQuery(name="LkpIndustry.findAll", query="SELECT l FROM LkpIndustry l")
public class LkpIndustry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="INDUSTRY_ID")
	private int industryId;

	@Column(name="INDUSTRY_DESCR")
	private String industryDescr;

	@Column(name="INDUSTRY_NAME")
	private String industryName;

	//bi-directional many-to-one association to BrgTopicsIndustry
	@OneToMany(mappedBy="lkpIndustry")
	private List<BrgTopicsIndustry> brgTopicsIndustries;

	//bi-directional many-to-one association to BrgUsrIndustry
	@OneToMany(mappedBy="lkpIndustry")
	private List<BrgUsrIndustry> brgUsrIndustries;

	//bi-directional many-to-one association to BrgUsrReqrIndustry
	@OneToMany(mappedBy="lkpIndustry")
	private List<BrgUsrReqrIndustry> brgUsrReqrIndustries;

	public LkpIndustry() {
	}

	public int getIndustryId() {
		return this.industryId;
	}

	public void setIndustryId(int industryId) {
		this.industryId = industryId;
	}

	public String getIndustryDescr() {
		return this.industryDescr;
	}

	public void setIndustryDescr(String industryDescr) {
		this.industryDescr = industryDescr;
	}

	public String getIndustryName() {
		return this.industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public List<BrgTopicsIndustry> getBrgTopicsIndustries() {
		return this.brgTopicsIndustries;
	}

	public void setBrgTopicsIndustries(List<BrgTopicsIndustry> brgTopicsIndustries) {
		this.brgTopicsIndustries = brgTopicsIndustries;
	}

	public BrgTopicsIndustry addBrgTopicsIndustry(BrgTopicsIndustry brgTopicsIndustry) {
		getBrgTopicsIndustries().add(brgTopicsIndustry);
		brgTopicsIndustry.setLkpIndustry(this);

		return brgTopicsIndustry;
	}

	public BrgTopicsIndustry removeBrgTopicsIndustry(BrgTopicsIndustry brgTopicsIndustry) {
		getBrgTopicsIndustries().remove(brgTopicsIndustry);
		brgTopicsIndustry.setLkpIndustry(null);

		return brgTopicsIndustry;
	}

	public List<BrgUsrIndustry> getBrgUsrIndustries() {
		return this.brgUsrIndustries;
	}

	public void setBrgUsrIndustries(List<BrgUsrIndustry> brgUsrIndustries) {
		this.brgUsrIndustries = brgUsrIndustries;
	}

	public BrgUsrIndustry addBrgUsrIndustry(BrgUsrIndustry brgUsrIndustry) {
		getBrgUsrIndustries().add(brgUsrIndustry);
		brgUsrIndustry.setLkpIndustry(this);

		return brgUsrIndustry;
	}

	public BrgUsrIndustry removeBrgUsrIndustry(BrgUsrIndustry brgUsrIndustry) {
		getBrgUsrIndustries().remove(brgUsrIndustry);
		brgUsrIndustry.setLkpIndustry(null);

		return brgUsrIndustry;
	}

	public List<BrgUsrReqrIndustry> getBrgUsrReqrIndustries() {
		return this.brgUsrReqrIndustries;
	}

	public void setBrgUsrReqrIndustries(List<BrgUsrReqrIndustry> brgUsrReqrIndustries) {
		this.brgUsrReqrIndustries = brgUsrReqrIndustries;
	}

	public BrgUsrReqrIndustry addBrgUsrReqrIndustry(BrgUsrReqrIndustry brgUsrReqrIndustry) {
		getBrgUsrReqrIndustries().add(brgUsrReqrIndustry);
		brgUsrReqrIndustry.setLkpIndustry(this);

		return brgUsrReqrIndustry;
	}

	public BrgUsrReqrIndustry removeBrgUsrReqrIndustry(BrgUsrReqrIndustry brgUsrReqrIndustry) {
		getBrgUsrReqrIndustries().remove(brgUsrReqrIndustry);
		brgUsrReqrIndustry.setLkpIndustry(null);

		return brgUsrReqrIndustry;
	}

}