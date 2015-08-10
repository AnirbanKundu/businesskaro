package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the lkp_targ_audience database table.
 * 
 */
@Entity
@Table(name="lkp_targ_audience")
@NamedQuery(name="LkpTargAudience.findAll", query="SELECT l FROM LkpTargAudience l")
public class LkpTargAudience implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TARG_AUD_ID")
	private int targAudId;

	@Column(name="TARF_AUD_TYP")
	private String tarfAudTyp;

	@Column(name="TARG_AUD_DISP_VAL")
	private String targAudDispVal;

	@Column(name="TARG_AUD_MAX_VAL")
	private int targAudMaxVal;

	@Column(name="TARG_AUD_MIN_VAL")
	private int targAudMinVal;

	public LkpTargAudience() {
	}

	public int getTargAudId() {
		return this.targAudId;
	}

	public void setTargAudId(int targAudId) {
		this.targAudId = targAudId;
	}

	public String getTarfAudTyp() {
		return this.tarfAudTyp;
	}

	public void setTarfAudTyp(String tarfAudTyp) {
		this.tarfAudTyp = tarfAudTyp;
	}

	public String getTargAudDispVal() {
		return this.targAudDispVal;
	}

	public void setTargAudDispVal(String targAudDispVal) {
		this.targAudDispVal = targAudDispVal;
	}

	public int getTargAudMaxVal() {
		return this.targAudMaxVal;
	}

	public void setTargAudMaxVal(int targAudMaxVal) {
		this.targAudMaxVal = targAudMaxVal;
	}

	public int getTargAudMinVal() {
		return this.targAudMinVal;
	}

	public void setTargAudMinVal(int targAudMinVal) {
		this.targAudMinVal = targAudMinVal;
	}

}