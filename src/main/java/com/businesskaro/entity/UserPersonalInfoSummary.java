package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tbl_user_pers_info_sumry database table.
 * 
 */
@Entity
@Table(name="tbl_user_pers_info_sumry")
@NamedQuery(name="UserPersonalInfoSummary.findAll", query="SELECT t FROM UserPersonalInfoSummary t")
public class UserPersonalInfoSummary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USR_ID")
	private int usrId;

	@Column(name="ABOUT_ME",columnDefinition="TEXT")
	private String aboutMe;

	@Column(name="CITY_NAME")
	private String cityName;

	@Column(name="COMPANY_URL")
	private String companyUrl;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DT")
	private Date createDt;

	@Column(name="FST_NAME")
	private String fstName;

	private String industry;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPD")
	private Date lastUpd;

	@Column(name="LST_NAME")
	private String lstName;

	@Column(name="STATE_NAME")
	private String stateName;

	@Column(name="USR_TYP")
	private String usrTyp;
	
	@Column(name="IMAGE_URL")
	private String imageUrl;

	//bi-directional many-to-one association to BrgUsrIndustry
	@OneToMany(mappedBy="tblUserPersInfoSumry")
	private List<BrgUsrIndustry> brgUsrIndustries;

	//bi-directional many-to-one association to BrgUsrLookingFor
	@OneToMany(mappedBy="tblUserPersInfoSumry")
	private List<BrgUsrLookingFor> brgUsrLookingFors;

	//bi-directional many-to-one association to BrgUsrSkill
	@OneToMany(mappedBy="tblUserPersInfoSumry")
	private List<UserSkill> brgUsrSkills;

	//bi-directional many-to-one association to TblUsrReqOffer
	@OneToMany(mappedBy="tblUserPersInfoSumry")
	private List<TblUsrReqOffer> tblUsrReqOffers;

	public UserPersonalInfoSummary() {
	}

	public int getUsrId() {
		return this.usrId;
	}

	public void setUsrId(int usrId) {
		this.usrId = usrId;
	}

	public String getAboutMe() {
		return this.aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCompanyUrl() {
		return this.companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}

	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getFstName() {
		return this.fstName;
	}

	public void setFstName(String fstName) {
		this.fstName = fstName;
	}

	public String getIndustry() {
		return this.industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public Date getLastUpd() {
		return this.lastUpd;
	}

	public void setLastUpd(Date lastUpd) {
		this.lastUpd = lastUpd;
	}

	public String getLstName() {
		return this.lstName;
	}

	public void setLstName(String lstName) {
		this.lstName = lstName;
	}

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getUsrTyp() {
		return this.usrTyp;
	}

	public void setUsrTyp(String usrTyp) {
		this.usrTyp = usrTyp;
	}
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<BrgUsrIndustry> getBrgUsrIndustries() {
		return this.brgUsrIndustries;
	}

	public void setBrgUsrIndustries(List<BrgUsrIndustry> brgUsrIndustries) {
		this.brgUsrIndustries = brgUsrIndustries;
	}

	public BrgUsrIndustry addBrgUsrIndustry(BrgUsrIndustry brgUsrIndustry) {
		getBrgUsrIndustries().add(brgUsrIndustry);
		brgUsrIndustry.setTblUserPersInfoSumry(this);

		return brgUsrIndustry;
	}

	public BrgUsrIndustry removeBrgUsrIndustry(BrgUsrIndustry brgUsrIndustry) {
		getBrgUsrIndustries().remove(brgUsrIndustry);
		brgUsrIndustry.setTblUserPersInfoSumry(null);

		return brgUsrIndustry;
	}

	public List<BrgUsrLookingFor> getBrgUsrLookingFors() {
		return this.brgUsrLookingFors;
	}

	public void setBrgUsrLookingFors(List<BrgUsrLookingFor> brgUsrLookingFors) {
		this.brgUsrLookingFors = brgUsrLookingFors;
	}

	public BrgUsrLookingFor addBrgUsrLookingFor(BrgUsrLookingFor brgUsrLookingFor) {
		getBrgUsrLookingFors().add(brgUsrLookingFor);
		brgUsrLookingFor.setTblUserPersInfoSumry(this);

		return brgUsrLookingFor;
	}

	public BrgUsrLookingFor removeBrgUsrLookingFor(BrgUsrLookingFor brgUsrLookingFor) {
		getBrgUsrLookingFors().remove(brgUsrLookingFor);
		brgUsrLookingFor.setTblUserPersInfoSumry(null);

		return brgUsrLookingFor;
	}

	public List<UserSkill> getBrgUsrSkills() {
		return this.brgUsrSkills;
	}

	public void setBrgUsrSkills(List<UserSkill> brgUsrSkills) {
		this.brgUsrSkills = brgUsrSkills;
	}

	public UserSkill addBrgUsrSkill(UserSkill brgUsrSkill) {
		getBrgUsrSkills().add(brgUsrSkill);
		brgUsrSkill.setTblUserPersInfoSumry(this);

		return brgUsrSkill;
	}

	public UserSkill removeBrgUsrSkill(UserSkill brgUsrSkill) {
		getBrgUsrSkills().remove(brgUsrSkill);
		brgUsrSkill.setTblUserPersInfoSumry(null);

		return brgUsrSkill;
	}

	public List<TblUsrReqOffer> getTblUsrReqOffers() {
		return this.tblUsrReqOffers;
	}

	public void setTblUsrReqOffers(List<TblUsrReqOffer> tblUsrReqOffers) {
		this.tblUsrReqOffers = tblUsrReqOffers;
	}

	public TblUsrReqOffer addTblUsrReqOffer(TblUsrReqOffer tblUsrReqOffer) {
		getTblUsrReqOffers().add(tblUsrReqOffer);
		tblUsrReqOffer.setTblUserPersInfoSumry(this);

		return tblUsrReqOffer;
	}

	public TblUsrReqOffer removeTblUsrReqOffer(TblUsrReqOffer tblUsrReqOffer) {
		getTblUsrReqOffers().remove(tblUsrReqOffer);
		tblUsrReqOffer.setTblUserPersInfoSumry(null);

		return tblUsrReqOffer;
	}

}