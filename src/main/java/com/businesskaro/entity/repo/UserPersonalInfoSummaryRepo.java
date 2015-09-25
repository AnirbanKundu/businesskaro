package com.businesskaro.entity.repo;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.UserPersonalInfoSummary;

@Transactional(value=TxType.MANDATORY)
public interface UserPersonalInfoSummaryRepo extends CrudRepository<UserPersonalInfoSummary, Integer> {
	
	/*
	@Query("update tbl_user_password set PROFILE_CREATED = b where USR_ID = pk")
	void updateFlag(Integer pk, Integer b);
	*/

}

