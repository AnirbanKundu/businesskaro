package com.businesskaro.entity.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.businesskaro.entity.TagEntity;
@Transactional(readOnly = true)
//@Entity
//@NamedQuery(name = "TagEntity.deleteByEntityIdAndEntityType", query = "delete from tag_entity where entity_id = ?1 and entity_type=?2 and tag_entity_id <> 0")
public interface TagEntityRepo extends CrudRepository<TagEntity, Integer> {

	void deleteAllByEntityId(Integer entityId);

	List<TagEntity> findAllByTagId(Integer id);

	List<TagEntity> findAllByTagIdAndEntityType(Integer id, String entityType);
	
	//void deleteByEntityIdAndEntityType(Integer entityId,String entityType);
	
	@Modifying
	@Transactional
	@Query(value="delete from TagEntity e where e.entityId = ?1 and entityType=?2")
	void deleteByEntityIdAndEntityType(Integer entityId,String entityType);
}
