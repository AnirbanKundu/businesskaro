package com.businesskaro.entity.repo;

import java.util.List;
//import javax.persistence.*;
import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.TagEntity;

//@Entity
//@NamedQuery(name = "TagEntity.deleteAllByEntityIdAndEntityType", query = "delete from tag_entity where entity_id = ?1 and entity_type=?2 and tag_entity_id <> 0")
public interface TagEntityRepo extends CrudRepository<TagEntity, Integer> {

	void deleteAllByEntityId(Integer entityId);

	List<TagEntity> findAllByTagId(Integer id);

	List<TagEntity> findAllByTagIdAndEntityType (Integer id, String entityType);
	
	//void deleteAllByEntityIdAndEntityType(Integer entityId,String entityType);
}
