package com.businesskaro.entity.repo;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.BrgUsrReqrState;

@Transactional
public interface BrgUsrReqrStateRepo extends CrudRepository<BrgUsrReqrState, Integer> {

}
