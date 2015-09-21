package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the lkp_skills database table.
 * 
 */
@Entity
@Table(name="lkp_skills")
@NamedQuery(name="LkpSkill.findAll", query="SELECT l FROM LkpSkill l")
public class LkpSkill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SKILL_ID")
	private int skillId;

	@Column(name="SKILL_DESCR")
	private String skillDescr;

	@Column(name="SKILL_NAME")
	private String skillName;

	//bi-directional many-to-one association to BrgUsrLookingFor
	@JsonIgnore
	@OneToMany(mappedBy="lkpSkill")
	private List<BrgUsrLookingFor> brgUsrLookingFors;

	//bi-directional many-to-one association to BrgUsrSkill
	@JsonIgnore
	@OneToMany(mappedBy="lkpSkill")
	private List<UserSkill> brgUsrSkills;

	public LkpSkill() {
	}

	public int getSkillId() {
		return this.skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public String getSkillDescr() {
		return this.skillDescr;
	}

	public void setSkillDescr(String skillDescr) {
		this.skillDescr = skillDescr;
	}

	public String getSkillName() {
		return this.skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public List<BrgUsrLookingFor> getBrgUsrLookingFors() {
		return this.brgUsrLookingFors;
	}

	public void setBrgUsrLookingFors(List<BrgUsrLookingFor> brgUsrLookingFors) {
		this.brgUsrLookingFors = brgUsrLookingFors;
	}

	public BrgUsrLookingFor addBrgUsrLookingFor(BrgUsrLookingFor brgUsrLookingFor) {
		getBrgUsrLookingFors().add(brgUsrLookingFor);
		brgUsrLookingFor.setLkpSkill(this);

		return brgUsrLookingFor;
	}

	public BrgUsrLookingFor removeBrgUsrLookingFor(BrgUsrLookingFor brgUsrLookingFor) {
		getBrgUsrLookingFors().remove(brgUsrLookingFor);
		brgUsrLookingFor.setLkpSkill(null);

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
		brgUsrSkill.setLkpSkill(this);

		return brgUsrSkill;
	}

	public UserSkill removeBrgUsrSkill(UserSkill brgUsrSkill) {
		getBrgUsrSkills().remove(brgUsrSkill);
		brgUsrSkill.setLkpSkill(null);

		return brgUsrSkill;
	}

}