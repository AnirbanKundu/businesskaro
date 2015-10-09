package com.businesskaro.entity.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.BrgTopicsIndustry;
import com.businesskaro.entity.TblPolicy;

public interface BrgTopicsIndustryRepo  extends CrudRepository<BrgTopicsIndustry, Integer>{

	public List<BrgTopicsIndustry> findByTblPolicy(TblPolicy policy);
}
