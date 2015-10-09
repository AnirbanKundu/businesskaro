package com.businesskaro.entity.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.BrgTopicsState;
import com.businesskaro.entity.TblPolicy;

public interface BrgTopicsStateRepo extends CrudRepository<BrgTopicsState, Integer>{

	public List<BrgTopicsState> findByTblPolicy(TblPolicy policy);
}
