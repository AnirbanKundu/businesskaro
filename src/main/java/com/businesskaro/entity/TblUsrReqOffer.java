package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.businesskaro.entity.UserPersonalInfoSummary;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tbl_usr_req_offer database table.
 * 
 */
@Entity
@Table(name="tbl_usr_req_offer")
@NamedQuery(name="TblUsrReqOffer.findAll", query="SELECT t FROM TblUsrReqOffer t")
public class TblUsrReqOffer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="REQ_OFFR_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer reqOffrId;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DT")
	private Date createDt;

	@Column(name="IMAGE_URL")
	private String imageUrl;

	@Column(name="is_verified")
	private int isVerified;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPD")
	private Date lastUpd;

	@Column(name="REQ_OFFR_DESC",columnDefinition="TEXT")
	private String reqOffrDesc;

	@Column(name="REQ_OFFR_TITLE")
	private String reqOffrTitle;

	@Column(name="REQ_OFFR_TYP")
	private String reqOffrTyp;

	@Column(name="TARG_AUDIENCE_ID")
	private int targAudienceId;

	//bi-directional many-to-one association to BrgUsrReqOfferQuestion
	@OneToMany(mappedBy="tblUsrReqOffer")
	private List<BrgUsrReqOfferQuestion> brgUsrReqOfferQuestions;

	//bi-directional many-to-one association to BrgUsrReqrIndustry
	@OneToMany(mappedBy="tblUsrReqOffer")
	private List<BrgUsrReqrIndustry> brgUsrReqrIndustries;

	//bi-directional many-to-one association to BrgUsrReqrState
	@OneToMany(mappedBy="tblUsrReqOffer")
	private List<BrgUsrReqrState> brgUsrReqrStates;

	//bi-directional many-to-one association to BrgUsrRequiremnt
	@OneToMany(mappedBy="tblUsrReqOffer")
	private List<BrgUsrRequiremnt> brgUsrRequiremnts;

	//bi-directional many-to-one association to TblUserPersInfoSumry
	@ManyToOne
	@JoinColumn(name="USR_ID")
	private UserPersonalInfoSummary tblUserPersInfoSumry;

	public TblUsrReqOffer() {
	}

	public Integer getReqOffrId() {
		return this.reqOffrId;
	}

	public void setReqOffrId(Integer reqOffrId) {
		this.reqOffrId = reqOffrId;
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

	public int getIsVerified() {
		return this.isVerified;
	}

	public void setIsVerified(int isVerified) {
		this.isVerified = isVerified;
	}

	public Date getLastUpd() {
		return this.lastUpd;
	}

	public void setLastUpd(Date lastUpd) {
		this.lastUpd = lastUpd;
	}

	public String getReqOffrDesc() {
		return this.reqOffrDesc;
	}

	public void setReqOffrDesc(String reqOffrDesc) {
		this.reqOffrDesc = reqOffrDesc;
	}

	public String getReqOffrTitle() {
		return this.reqOffrTitle;
	}

	public void setReqOffrTitle(String reqOffrTitle) {
		this.reqOffrTitle = reqOffrTitle;
	}

	public String getReqOffrTyp() {
		return this.reqOffrTyp;
	}

	public void setReqOffrTyp(String reqOffrTyp) {
		this.reqOffrTyp = reqOffrTyp;
	}

	public int getTargAudienceId() {
		return this.targAudienceId;
	}

	public void setTargAudienceId(int targAudienceId) {
		this.targAudienceId = targAudienceId;
	}

	public List<BrgUsrReqOfferQuestion> getBrgUsrReqOfferQuestions() {
		return this.brgUsrReqOfferQuestions;
	}

	public void setBrgUsrReqOfferQuestions(List<BrgUsrReqOfferQuestion> brgUsrReqOfferQuestions) {
		this.brgUsrReqOfferQuestions = brgUsrReqOfferQuestions;
	}

	public BrgUsrReqOfferQuestion addBrgUsrReqOfferQuestion(BrgUsrReqOfferQuestion brgUsrReqOfferQuestion) {
		getBrgUsrReqOfferQuestions().add(brgUsrReqOfferQuestion);
		brgUsrReqOfferQuestion.setTblUsrReqOffer(this);

		return brgUsrReqOfferQuestion;
	}

	public BrgUsrReqOfferQuestion removeBrgUsrReqOfferQuestion(BrgUsrReqOfferQuestion brgUsrReqOfferQuestion) {
		getBrgUsrReqOfferQuestions().remove(brgUsrReqOfferQuestion);
		brgUsrReqOfferQuestion.setTblUsrReqOffer(null);

		return brgUsrReqOfferQuestion;
	}

	public List<BrgUsrReqrIndustry> getBrgUsrReqrIndustries() {
		return this.brgUsrReqrIndustries;
	}

	public void setBrgUsrReqrIndustries(List<BrgUsrReqrIndustry> brgUsrReqrIndustries) {
		this.brgUsrReqrIndustries = brgUsrReqrIndustries;
	}

	public BrgUsrReqrIndustry addBrgUsrReqrIndustry(BrgUsrReqrIndustry brgUsrReqrIndustry) {
		getBrgUsrReqrIndustries().add(brgUsrReqrIndustry);
		brgUsrReqrIndustry.setTblUsrReqOffer(this);

		return brgUsrReqrIndustry;
	}

	public BrgUsrReqrIndustry removeBrgUsrReqrIndustry(BrgUsrReqrIndustry brgUsrReqrIndustry) {
		getBrgUsrReqrIndustries().remove(brgUsrReqrIndustry);
		brgUsrReqrIndustry.setTblUsrReqOffer(null);

		return brgUsrReqrIndustry;
	}

	public List<BrgUsrReqrState> getBrgUsrReqrStates() {
		return this.brgUsrReqrStates;
	}

	public void setBrgUsrReqrStates(List<BrgUsrReqrState> brgUsrReqrStates) {
		this.brgUsrReqrStates = brgUsrReqrStates;
	}

	public BrgUsrReqrState addBrgUsrReqrState(BrgUsrReqrState brgUsrReqrState) {
		getBrgUsrReqrStates().add(brgUsrReqrState);
		brgUsrReqrState.setTblUsrReqOffer(this);

		return brgUsrReqrState;
	}

	public BrgUsrReqrState removeBrgUsrReqrState(BrgUsrReqrState brgUsrReqrState) {
		getBrgUsrReqrStates().remove(brgUsrReqrState);
		brgUsrReqrState.setTblUsrReqOffer(null);

		return brgUsrReqrState;
	}

	public List<BrgUsrRequiremnt> getBrgUsrRequiremnts() {
		return this.brgUsrRequiremnts;
	}

	public void setBrgUsrRequiremnts(List<BrgUsrRequiremnt> brgUsrRequiremnts) {
		this.brgUsrRequiremnts = brgUsrRequiremnts;
	}

	public BrgUsrRequiremnt addBrgUsrRequiremnt(BrgUsrRequiremnt brgUsrRequiremnt) {
		getBrgUsrRequiremnts().add(brgUsrRequiremnt);
		brgUsrRequiremnt.setTblUsrReqOffer(this);

		return brgUsrRequiremnt;
	}

	public BrgUsrRequiremnt removeBrgUsrRequiremnt(BrgUsrRequiremnt brgUsrRequiremnt) {
		getBrgUsrRequiremnts().remove(brgUsrRequiremnt);
		brgUsrRequiremnt.setTblUsrReqOffer(null);

		return brgUsrRequiremnt;
	}

	public UserPersonalInfoSummary getTblUserPersInfoSumry() {
		return this.tblUserPersInfoSumry;
	}

	public void setTblUserPersInfoSumry(UserPersonalInfoSummary tblUserPersInfoSumry) {
		this.tblUserPersInfoSumry = tblUserPersInfoSumry;
	}

}