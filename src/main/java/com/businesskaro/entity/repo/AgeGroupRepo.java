package com.businesskaro.entity.repo;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.AgeGroup;

@Transactional(value=TxType.MANDATORY)
public interface AgeGroupRepo extends CrudRepository<AgeGroup, Integer> {

	@Override
	public List<AgeGroup> findAll();
}
