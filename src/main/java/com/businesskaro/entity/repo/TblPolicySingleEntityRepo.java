package com.businesskaro.entity.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.TblPolicy;
import com.businesskaro.entity.TblPolicySingleEntity;

public interface TblPolicySingleEntityRepo extends CrudRepository<TblPolicySingleEntity, Integer>{
	List<TblPolicySingleEntity> findAllByIsFeatured(Integer isFeatured);
	List<TblPolicySingleEntity> findAllByUsrId(Integer usrID);

}
