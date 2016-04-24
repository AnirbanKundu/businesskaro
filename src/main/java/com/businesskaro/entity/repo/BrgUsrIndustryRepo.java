package com.businesskaro.entity.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.BrgUsrIndustry;
import com.businesskaro.entity.UserPersonalInfoSummary;

public interface BrgUsrIndustryRepo extends CrudRepository<BrgUsrIndustry, Integer> {
	
	public List<BrgUsrIndustry> findByTblUserPersInfoSumry(UserPersonalInfoSummary tblUserPersInfoSumry);

}
