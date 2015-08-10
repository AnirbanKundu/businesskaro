package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the brg_usr_requiremnt database table.
 * 
 */
@Entity
@Table(name="brg_usr_requiremnt")
@NamedQuery(name="BrgUsrRequiremnt.findAll", query="SELECT b FROM BrgUsrRequiremnt b")
public class BrgUsrRequiremnt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="IDEA_REQR_ID")
	private int ideaReqrId;

	@Column(name="RESPONSE_ID")
	private String responseId;

	//bi-directional many-to-one association to TblUsrReqOffer
	@ManyToOne
	@JoinColumn(name="REQ_OFFR_ID")
	private TblUsrReqOffer tblUsrReqOffer;

	//bi-directional many-to-one association to LkpQuestion
	@ManyToOne
	@JoinColumn(name="REQ_OFFR_QUEST_ID")
	private LkpQuestion lkpQuestion;

	public BrgUsrRequiremnt() {
	}

	public int getIdeaReqrId() {
		return this.ideaReqrId;
	}

	public void setIdeaReqrId(int ideaReqrId) {
		this.ideaReqrId = ideaReqrId;
	}

	public String getResponseId() {
		return this.responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
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