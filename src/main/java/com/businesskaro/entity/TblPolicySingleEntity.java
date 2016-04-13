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

@NamedQueries
(
    {
        @NamedQuery(name="TblPolicySingleEntity.getPolicyByFeature", query="SELECT t FROM TblPolicy t where t.isFeatured = :isFeatured")    
    }
)

public class TblPolicySingleEntity implements Serializable {
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

	//ALTERED HERE
	@Column(name = "POLICY_DESC", columnDefinition="TEXT")
	private String policyDesc;

	@Column(name = "POLICY_TITLE")
	private String policyTitle;

	@Column(name = "POLICY_TYPE")
	private String policyType;
	
	//ADDED HERE
	@Column(name="IS_FEATURED")
	private int	isFeatured;	
	
	@Column(name="USR_ID") 
	private int usrId;

	public TblPolicySingleEntity() {
	}

	public int getPolicyId() {
		return policyId;
	}

	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getLastUpd() {
		return lastUpd;
	}

	public void setLastUpd(Date lastUpd) {
		this.lastUpd = lastUpd;
	}

	public String getPolicyDesc() {
		return policyDesc;
	}

	public void setPolicyDesc(String policyDesc) {
		this.policyDesc = policyDesc;
	}

	public String getPolicyTitle() {
		return policyTitle;
	}

	public void setPolicyTitle(String policyTitle) {
		this.policyTitle = policyTitle;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public int getIsFeatured() {
		return isFeatured;
	}

	public void setIsFeatured(int isFeatured) {
		this.isFeatured = isFeatured;
	}

	public int getUsrId() {
		return usrId;
	}

	public void setUsrId(int usrId) {
		this.usrId = usrId;
	}
	
}