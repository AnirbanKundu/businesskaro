package com.businesskaro.entity.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.TblUsrReqOffer;
import com.businesskaro.entity.UserPersonalInfoSummary;

public interface TblUsrReqOfferRepo extends CrudRepository<TblUsrReqOffer, Integer> {

	public List<TblUsrReqOffer> findAllByTblUserPersInfoSumry(UserPersonalInfoSummary entity);
	
}
