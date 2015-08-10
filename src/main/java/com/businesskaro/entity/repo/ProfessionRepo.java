package com.businesskaro.entity.repo;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.LkpProfession;

public interface ProfessionRepo extends CrudRepository<LkpProfession, Integer> {
	
}
