package com.businesskaro.entity.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.LkpIndustry;

public interface UserInductryRepo extends CrudRepository<LkpIndustry, Integer> {

	@Override
	public List<LkpIndustry> findAll();
}
