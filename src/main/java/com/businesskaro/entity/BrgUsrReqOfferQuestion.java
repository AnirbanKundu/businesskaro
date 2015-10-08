package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the brg_usr_req_offer_questions database table.
 * 
 */
@Entity
@Table(name="brg_usr_req_offer_questions")
public class BrgUsrReqOfferQuestion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String response;

	//bi-directional many-to-one association to TblUsrReqOffer
	@ManyToOne
	@JoinColumn(name="OFFER_ID")
	private TblUsrReqOffer tblUsrReqOffer;

	//bi-directional many-to-one association to LkpQuestion
	@ManyToOne
	@JoinColumn(name="QUESTION_ID")
	private LkpQuestion lkpQuestion;

	public BrgUsrReqOfferQuestion() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResponse() {
		return this.response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public TblUsrReqOffer getTblUsrReqOffer() {
		return this.tblUsrReqOffer;
	}

	public void setTblUsrReqOffer(TblUsrReqOffer tblUsrReqOffer) {
		this.tblUsrReqOffer = tblUsrReqOffer;
	}

	public LkpQuestion getLkpQuestion() {
		return this.lkpQuestion;
	}

	public void setLkpQuestion(LkpQuestion lkpQuestion) {
		this.lkpQuestion = lkpQuestion;
	}

}