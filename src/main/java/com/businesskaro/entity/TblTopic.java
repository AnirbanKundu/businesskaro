package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tbl_topics database table.
 * 
 */
@Entity
@Table(name="tbl_topics")
@NamedQuery(name="TblTopic.findAll", query="SELECT t FROM TblTopic t")
public class TblTopic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TOPIC_ID")
	private int topicId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_DT")
	private Date createDt;

	@Column(name="IMAGE_URL")
	private String imageUrl;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_UPD")
	private Date lastUpd;

	@Column(name="TOPIC_DESC",columnDefinition="TEXT")
	private String topicDesc;

	@Column(name="TOPIC_TITLE")
	private String topicTitle;

	@Column(name="TOPIC_TYPE")
	private String topicType;

	public TblTopic() {
	}

	public int getTopicId() {
		return this.topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
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

	public String getTopicDesc() {
		return this.topicDesc;
	}

	public void setTopicDesc(String topicDesc) {
		this.topicDesc = topicDesc;
	}

	public String getTopicTitle() {
		return this.topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public String getTopicType() {
		return this.topicType;
	}

	public void setTopicType(String topicType) {
		this.topicType = topicType;
	}

}