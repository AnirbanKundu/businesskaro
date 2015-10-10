package com.businesskaro.entity.repo;

import java.util.List;


import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.BrgUsrReqOfferQuestion;
import com.businesskaro.entity.TblUsrReqOffer;

@Transactional
public interface BrgUsrReqOfferQuestionRepo extends CrudRepository<BrgUsrReqOfferQuestion, Integer>{
	List<BrgUsrReqOfferQuestion> findByTblUsrReqOffer(TblUsrReqOffer entity);
}
