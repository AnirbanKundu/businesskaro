package com.businesskaro.entity.repo;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.BrgUsrReqrIndustry;
import com.businesskaro.entity.TblUsrReqOffer;

@Transactional
public interface BrgUsrReqrIndustryRepo extends CrudRepository<BrgUsrReqrIndustry, Integer> {
	List<BrgUsrReqrIndustry> findByTblUsrReqOffer(TblUsrReqOffer entity);

}
