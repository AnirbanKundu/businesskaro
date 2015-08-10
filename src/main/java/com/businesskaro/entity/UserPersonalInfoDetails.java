package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tbl_user_pers_info_detail database table.
 * 
 */
@Entity
@Table(name="tbl_user_pers_info_detail")
@NamedQuery(name="UserPersonalInfoDetails.findAll", query="SELECT t FROM UserPersonalInfoDetails t")
public class UserPersonalInfoDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USR_ID")
	private int usrId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_DT")
	private Date createDt;

	@Column(name="FB_URL")
	private String fbUrl;

	@Column(name="IMAGE_URL")
	private String imageUrl;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_UPD")
	private Date lastUpd;

	@Column(name="LINKD_IN_URL")
	private String linkdInUrl;

	@Column(name="TWITR_URL")
	private String twitrUrl;

	//bi-directional many-to-one association to LkpAgeGrp
	@ManyToOne
	@JoinColumn(name="AGE_ID")
	private AgeGroup lkpAgeGrp;

	//bi-directional many-to-one association to LkpEducation
	@ManyToOne
	@JoinColumn(name="EDUCATION_ID")
	private Education lkpEducation;

	//bi-directional many-to-one association to LkpExperience
	@ManyToOne
	@JoinColumn(name="EXPERIENCE_ID")
	private LkpExperience lkpExperience;

	//bi-directional many-to-one association to LkpProfession
	@ManyToOne
	@JoinColumn(name="PROFESSION_ID")
	private LkpProfession lkpProfession;

	//bi-directional many-to-one association to LkpState
	@ManyToOne
	@JoinColumn(name="STATE_ID")
	private LkpState lkpState;

	public UserPersonalInfoDetails() {
	}

	public int getUsrId() {
		return this.usrId;
	}

	public void setUsrId(int usrId) {
		this.usrId = usrId;
	}

	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getFbUrl() {
		return this.fbUrl;
	}

	public void setFbUrl(String fbUrl) {
		this.fbUrl = fbUrl;
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

	public String getLinkdInUrl() {
		return this.linkdInUrl;
	}

	public void setLinkdInUrl(String linkdInUrl) {
		this.linkdInUrl = linkdInUrl;
	}

	public String getTwitrUrl() {
		return this.twitrUrl;
	}

	public void setTwitrUrl(String twitrUrl) {
		this.twitrUrl = twitrUrl;
	}

	public AgeGroup getLkpAgeGrp() {
		return this.lkpAgeGrp;
	}

	public void setLkpAgeGrp(AgeGroup lkpAgeGrp) {
		this.lkpAgeGrp = lkpAgeGrp;
	}

	public Education getLkpEducation() {
		return this.lkpEducation;
	}

	public void setLkpEducation(Education lkpEducation) {
		this.lkpEducation = lkpEducation;
	}

	public LkpExperience getLkpExperience() {
		return this.lkpExperience;
	}

	public void setLkpExperience(LkpExperience lkpExperience) {
		this.lkpExperience = lkpExperience;
	}

	public LkpProfession getLkpProfession() {
		return this.lkpProfession;
	}

	public void setLkpProfession(LkpProfession lkpProfession) {
		this.lkpProfession = lkpProfession;
	}

	public LkpState getLkpState() {
		return this.lkpState;
	}

	public void setLkpState(LkpState lkpState) {
		this.lkpState = lkpState;
	}

}