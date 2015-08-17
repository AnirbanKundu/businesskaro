package com.businesskaro.entity.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.LkpExperience;

public interface ExperienceRepo extends CrudRepository<LkpExperience, Integer> {

	@Override
	public List<LkpExperience> findAll();
}
