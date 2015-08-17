package com.businesskaro.entity.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.LkpProfession;

public interface ProfessionRepo extends CrudRepository<LkpProfession, Integer> {
	@Override
	public List<LkpProfession> findAll();
}
