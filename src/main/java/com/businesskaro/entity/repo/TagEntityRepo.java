package com.businesskaro.entity.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.TagEntity;

 
public interface TagEntityRepo extends CrudRepository<TagEntity, Integer> {

	void deleteAllByEntityId(Integer entityId);

	List<TagEntity> findAllByTagId(Integer id);

	List<TagEntity> findAllByTagIdAndEntityType (Integer id, String entityType);

}
