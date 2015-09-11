package com.businesskaro.entity.repo;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.BrgUsrLookingFor;

@Transactional
public interface BrgUsrReqOfferQuestionRepo extends CrudRepository<BrgUsrLookingFor, Integer>{

}
