package com.businesskaro.entity.repo;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.Tag;

 
public interface TagRepo extends CrudRepository<Tag, Integer>{

	List<Tag> findByNameContainingIgnoreCase(String tagName);
	
	Tag findByNameIgnoreCase(String tagName);
	
	public List<Tag> findAll();

}
