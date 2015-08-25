package com.businesskaro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.businesskaro.entity.Tag;
import com.businesskaro.entity.TagEntity;
import com.businesskaro.entity.repo.TagEntityRepo;
import com.businesskaro.entity.repo.TagRepo;
import com.businesskaro.rest.dto.TagEntityRequest;
import com.businesskaro.rest.dto.TagItem;

@Service
public class TagService {

	@Autowired
	TagRepo tagRepo;

	@Autowired
	TagEntityRepo tagEntityRepo;

	public void createTagEntry(com.businesskaro.rest.dto.TagEntityRequest request) {

		clearTagEntry(request);

		for (TagItem tag : request.newTags) {
			Tag tagEntity = tagRepo.findByNameIgnoreCase(tag.name);
			if (tagEntity == null) {
				tagEntity = new Tag();
				tagEntity.setName(tag.name);
				tagEntity = tagRepo.save(tagEntity);
			}
			TagEntity entityTag = new TagEntity();
			entityTag.setTagId(tagEntity.getTagId());
			entityTag.setEntityType(request.entityType.toString());
			entityTag.setEntityId(request.entityId);
			tagEntityRepo.save(entityTag);
		}
		
		for (TagItem tag : request.existingtags) {
			TagEntity entityTag = new TagEntity();
			entityTag.setTagId(tag.tagId);
			entityTag.setEntityType(request.entityType.toString());
			entityTag.setEntityId(request.entityId);
			tagEntityRepo.save(entityTag);
		}	
	}

	private void clearTagEntry(TagEntityRequest request) {
		tagEntityRepo.deleteAllByEntityId(request.entityId);
	}

	public List<Tag> getTagNames(String keyword) {
		return tagRepo.findByNameIgnoreCaseStartsWith(keyword);
	}

	public List<TagEntity> searchForTagName(String tagName) {

		List<TagEntity> results = new ArrayList<TagEntity>();

		List<TagEntity> resuts = new ArrayList<TagEntity>();
		List<Tag> tags = tagRepo.findByNameLikeIgnoreCase(tagName);
		if (tags.size() == 0) {
			return resuts;
		}

		for (Tag tag : tags) {
			results.addAll(tagEntityRepo.findAllByTagId(tag.getTagId()));
		}

		return results;
	}

	public List<TagEntity> searchForTagNameAndEntityType(String tagName,
			String entityType) {
		List<TagEntity> results = new ArrayList<TagEntity>();

		List<TagEntity> resuts = new ArrayList<TagEntity>();
		List<Tag> tags = tagRepo.findByNameContainingIgnoreCase(tagName);
		if (tags.size() == 0) {
			return resuts;
		}

		for (Tag tag : tags) {
			results.addAll(tagEntityRepo.findAllByTagIdAndEntityType(
					tag.getTagId(), entityType));
		}
		return results;
	}

}
