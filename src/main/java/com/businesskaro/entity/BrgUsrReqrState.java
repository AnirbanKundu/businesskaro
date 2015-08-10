package com.businesskaro.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the brg_usr_reqr_state database table.
 * 
 */
@Entity
@Table(name="brg_usr_reqr_state")
@NamedQuery(name="BrgUsrReqrState.findAll", query="SELECT b FROM BrgUsrReqrState b")
public class BrgUsrReqrState implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="REQR_INDUS")
	private int reqrIndus;

	//bi-directional many-to-one association to TblUsrReqOffer
	@ManyToOne
	@JoinColumn(name="REQ_OFFR_ID")
	private TblUsrReqOffer tblUsrReqOffer;

	//bi-directional many-to-one association to LkpState
	@ManyToOne
	@JoinColumn(name="STATE_ID")
	private LkpState lkpState;

	public BrgUsrReqrState() {
	}

	public int getReqrIndus() {
		return this.reqrIndus;
	}

	public void setReqrIndus(int reqrIndus) {
		this.reqrIndus = reqrIndus;
	}

	public TblUsrReqOffer getTblUsrReqOffer() {
		return this.tblUsrReqOffer;
	}

	public void setTblUsrReqOffer(TblUsrReqOffer tblUsrReqOffer) {
		this.tblUsrReqOffer = tblUsrReqOffer;
	}

	public LkpState getLkpState() {
		return this.lkpState;
	}

	public void setLkpState(LkpState lkpState) {
		this.lkpState = lkpState;
	}

}