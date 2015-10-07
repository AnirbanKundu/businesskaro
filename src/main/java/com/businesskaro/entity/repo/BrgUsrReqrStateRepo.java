package com.businesskaro.entity.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.BrgUsrReqrState;
import com.businesskaro.entity.TblUsrReqOffer;

@Transactional
public interface BrgUsrReqrStateRepo extends CrudRepository<BrgUsrReqrState, Integer> {
	List<BrgUsrReqrState> findByTblUsrReqOffer(TblUsrReqOffer entity);
}
