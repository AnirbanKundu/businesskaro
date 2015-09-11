package com.businesskaro.entity.repo;


import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.BrgUsrReqrIndustry;

@Transactional
public interface BrgUsrReqrIndustryRepo extends CrudRepository<BrgUsrReqrIndustry, Integer> {

}
