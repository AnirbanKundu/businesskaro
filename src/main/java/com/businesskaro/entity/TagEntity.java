package com.businesskaro.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the tag_entity database table.
 * 
 */
@Entity
@Table(name="tag_entity")
@NamedQuery(name="TagEntity.findAll", query="SELECT t FROM TagEntity t")
public class TagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tag_entity_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int tagEntityId;

	@Column(name="entity_id")
	private int entityId;

	@Column(name="entity_type")
	private String entityType;

	@Column(name="tag_id")
	private int tagId;

	public TagEntity() {
	}

	public int getTagEntityId() {
		return this.tagEntityId;
	}

	public void setTagEntityId(int tagEntityId) {
		this.tagEntityId = tagEntityId;
	}

	public int getEntityId() {
		return this.entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public String getEntityType() {
		return this.entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public int getTagId() {
		return this.tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

}