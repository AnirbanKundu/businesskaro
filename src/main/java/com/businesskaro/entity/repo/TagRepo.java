package com.businesskaro.entity.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.Tag;

 
public interface TagRepo extends CrudRepository<Tag, Integer>{

	List<Tag> findByNameContainingIgnoreCase(String tagName);
	
	Tag findByNameIgnoreCase(String tagName);
	
	public List<Tag> findAll();
	
	List<Tag> findByNameLikeIgnoreCase(String keyword);
	
	List<Tag> findByNameIgnoreCaseStartsWith(String keyword);

}
