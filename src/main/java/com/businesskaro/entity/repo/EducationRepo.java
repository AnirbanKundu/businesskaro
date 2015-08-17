package com.businesskaro.entity.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.Education;

public interface EducationRepo extends CrudRepository<Education, Integer>{

	@Override
	public List<Education> findAll();
}
