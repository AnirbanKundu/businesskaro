package com.businesskaro.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.businesskaro.entity.Tag;
import com.businesskaro.entity.TagEntity;
import com.businesskaro.entity.repo.TagEntityRepo;
import com.businesskaro.entity.repo.TagRepo;
import com.businesskaro.rest.dto.TagEntityRequest;

@Service
public class TagService {

	@Autowired
	TagRepo tagRepo;

	@Autowired
	TagEntityRepo tagEntityRepo;

	public void createTagEntry(com.businesskaro.rest.dto.TagEntityRequest request) {

		clearTagEntry(request);

		for (String tag : request.tags) {
			Tag tagEntity = tagRepo.findByNameIgnoreCase(tag);
			if (tagEntity == null) {
				tagEntity = new Tag();
				tagEntity.setName(tag );
				tagEntity = tagRepo.save(tagEntity);
			}
			TagEntity entityTag = new TagEntity();
			entityTag.setTagId(tagEntity.getTagId());
			entityTag.setEntityType(request.entityType.toString());
			entityTag.setEntityId(request.entityId);
			entityTag.setCreatedDate(new Date());
			tagEntityRepo.save(entityTag);
		}
	}

	private void clearTagEntry(TagEntityRequest request) {
		tagEntityRepo.deleteAllByEntityId(request.entityId);
	}

	public List<Tag> getTagNames(String keyword) {
		return tagRepo.findByNameIgnoreCaseStartsWith(keyword);
	}

	public List<TagEntity> searchForTagName(String[] tagNames) {

		List<TagEntity> results = new ArrayList<TagEntity>();
		for (String tagName : tagNames) {
			List<Tag> tags = tagRepo.findByNameLikeIgnoreCase(tagName);
			if (tags.size() == 0) {
				break;
			}
			for (Tag tag : tags) {
				results.addAll(tagEntityRepo.findAllByTagId(tag.getTagId()));
			}
		}
		
		if(tagNames.length == 1){
			return results;
		}
		return findSubSet(results,tagNames.length);
	}

	public List<TagEntity> searchForTagNameAndEntityType(String[] tagNames,
			String entityType) {
		
		List<TagEntity> results = new ArrayList<TagEntity>();
		for(String tagName : tagNames) {
			List<Tag> tags = tagRepo.findByNameContainingIgnoreCase(tagName);
			if (tags.size() == 0) {
				break;
			}
			for (Tag tag : tags) {
				results.addAll(tagEntityRepo.findAllByTagIdAndEntityType(
						tag.getTagId(), entityType));
			}
		}
		
		if(tagNames.length == 1){
			return results;
		}
		return findSubSet(results,tagNames.length);
	}
	
	 public List<TagEntity> findSubSet(List<TagEntity> results, int count){
		 Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		 List<TagEntity> finalResult = new ArrayList<TagEntity>();
		 for(TagEntity entity : results){
			 if(map.containsKey(entity.getEntityId())){
				 int c = map.get(entity.getEntityId());
				 c=c+1;
				 map.put(entity.getEntityId(), c);
				 if(c == count){
					 finalResult.add(entity);
				 }
			 }else{
				 map.put(entity.getEntityId(), 1);
			 }
		 }
		 return finalResult;
	 }
}
