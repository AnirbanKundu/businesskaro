package com.businesskaro.entity.repo;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.BrgUsrReqOfferQuestion;

@Transactional
public interface BrgUsrReqOfferQuestionRepo extends CrudRepository<BrgUsrReqOfferQuestion, Integer>{

}
