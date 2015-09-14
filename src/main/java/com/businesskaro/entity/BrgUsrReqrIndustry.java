package com.businesskaro.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the brg_usr_reqr_industry database table.
 * 
 */
@Entity
@Table(name="brg_usr_reqr_industry")
@NamedQuery(name="BrgUsrReqrIndustry.findAll", query="SELECT b FROM BrgUsrReqrIndustry b")
public class BrgUsrReqrIndustry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="REQR_INDUS")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int reqrIndus;

	//bi-directional many-to-one association to TblUsrReqOffer
	@ManyToOne
	@JoinColumn(name="REQ_OFFR_ID")
	private TblUsrReqOffer tblUsrReqOffer;

	//bi-directional many-to-one association to LkpIndustry
	@ManyToOne
	@JoinColumn(name="INDUSTRY_ID")
	private LkpIndustry lkpIndustry;

	public BrgUsrReqrIndustry() {
	}

	public int getReqrIndus() {
		return this.reqrIndus;
	}

	public void setReqrIndus(int reqrIndus) {
		this.reqrIndus = reqrIndus;
	}

	public TblUsrReqOffer getTblUsrReqOffer() {
		return this.tblUsrReqOffer;
	}

	public void setTblUsrReqOffer(TblUsrReqOffer tblUsrReqOffer) {
		this.tblUsrReqOffer = tblUsrReqOffer;
	}

	public LkpIndustry getLkpIndustry() {
		return this.lkpIndustry;
	}

	public void setLkpIndustry(LkpIndustry lkpIndustry) {
		this.lkpIndustry = lkpIndustry;
	}

}