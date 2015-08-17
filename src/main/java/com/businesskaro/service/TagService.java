package com.businesskaro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.businesskaro.entity.Tag;
import com.businesskaro.entity.TagEntity;
import com.businesskaro.entity.repo.TagEntityRepo;
import com.businesskaro.entity.repo.TagRepo;
import com.businesskaro.model.TagEntityRequest;

@Service
public class TagService {

	@Autowired
	TagRepo tagRepo;

	@Autowired
	TagEntityRepo tagEntityRepo;

	public void createTagEntry(TagEntityRequest request) {

		clearTagEntry(request);

		for (String tagName : request.tags) {
			Tag tag = tagRepo.findByNameIgnoreCase(tagName);
			if (tag == null) {
				tag = new Tag();
				tag.setName(tagName);

				tag = tagRepo.save(tag);
			}

			TagEntity entityTag = new TagEntity();
			entityTag.setTagId(tag.getTagId());
			entityTag.setEntityType(request.entityType.toString());
			entityTag.setEntityId(request.entityId);
			tagEntityRepo.save(entityTag);
		}
	}

	private void clearTagEntry(TagEntityRequest request) {
		tagEntityRepo.deleteAllByEntityId(request.entityId);
	}

	public List<Tag> getTagNames() {
		return tagRepo.findAll();
	}

	public List<TagEntity> searchForTagName(String tagName) {

		List<TagEntity> results = new ArrayList<TagEntity>();

		List<TagEntity> resuts = new ArrayList<TagEntity>();
		List<Tag> tags = tagRepo.findByNameContainingIgnoreCase(tagName);
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
