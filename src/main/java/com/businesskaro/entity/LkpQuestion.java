package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the lkp_questions database table.
 * 
 */
@Entity
@Table(name="lkp_questions")
@NamedQuery(name="LkpQuestion.findAll", query="SELECT l FROM LkpQuestion l")
public class LkpQuestion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="QUEST_ID")
	private int questId;

	@Column(name="QUEST_TXT")
	private String questTxt;

	@Column(name="QUEST_TYP")
	private String questTyp;

	@Column(name="RESPONSE_TYP")
	private String responseTyp;

	//bi-directional many-to-one association to BrgUsrRequiremnt
	@OneToMany(mappedBy="lkpQuestion")
	private List<BrgUsrRequiremnt> brgUsrRequiremnts;

	public LkpQuestion() {
	}

	public int getQuestId() {
		return this.questId;
	}

	public void setQuestId(int questId) {
		this.questId = questId;
	}

	public String getQuestTxt() {
		return this.questTxt;
	}

	public void setQuestTxt(String questTxt) {
		this.questTxt = questTxt;
	}

	public String getQuestTyp() {
		return this.questTyp;
	}

	public void setQuestTyp(String questTyp) {
		this.questTyp = questTyp;
	}

	public String getResponseTyp() {
		return this.responseTyp;
	}

	public void setResponseTyp(String responseTyp) {
		this.responseTyp = responseTyp;
	}

	public List<BrgUsrRequiremnt> getBrgUsrRequiremnts() {
		return this.brgUsrRequiremnts;
	}

	public void setBrgUsrRequiremnts(List<BrgUsrRequiremnt> brgUsrRequiremnts) {
		this.brgUsrRequiremnts = brgUsrRequiremnts;
	}

	public BrgUsrRequiremnt addBrgUsrRequiremnt(BrgUsrRequiremnt brgUsrRequiremnt) {
		getBrgUsrRequiremnts().add(brgUsrRequiremnt);
		brgUsrRequiremnt.setLkpQuestion(this);

		return brgUsrRequiremnt;
	}

	public BrgUsrRequiremnt removeBrgUsrRequiremnt(BrgUsrRequiremnt brgUsrRequiremnt) {
		getBrgUsrRequiremnts().remove(brgUsrRequiremnt);
		brgUsrRequiremnt.setLkpQuestion(null);

		return brgUsrRequiremnt;
	}

}