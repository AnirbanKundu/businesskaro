package com.businesskaro.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.entity.Tag;
import com.businesskaro.entity.TagEntity;
import com.businesskaro.entity.repo.CustomRepository;
import com.businesskaro.model.BKEntityType;
import com.businesskaro.rest.dto.TagEntityRequest;
import com.businesskaro.service.TagService;

@RestController
public class TagRestService {

	@Autowired
	TagService tagService;
	
	@Autowired
	CustomRepository customRepo;
	
	@RequestMapping(value="/services/tag", method = RequestMethod.POST)
	public void createTag(@RequestBody TagEntityRequest request){
		tagService.createTagEntry(request);
	}
	
	@RequestMapping(value="/services/tag/names", method = RequestMethod.GET)
	public List<Tag> getTAgNames(@RequestParam("keyword") String keyword){
		return tagService.getTagNames(keyword);
	}
	
	@RequestMapping(value="/services/tag/entity", method = RequestMethod.GET)
	public List<TagEntity> searchTag(@RequestParam("keywords") String keywords , @RequestParam("entityType") BKEntityType entityType ){
		
		String[] keywordArr = keywords.split(",");
		System.out.println(keywordArr);
		if(entityType == BKEntityType.ALL){
			return tagService.searchForTagName(keywordArr);
		}else{
			return tagService.searchForTagNameAndEntityType(keywordArr, entityType.name());
		}
	}
	
	public List<TagEntity> searchTag1(@RequestParam("keywords") String keywords , @RequestParam("entityType") BKEntityType entityType ){
		String[] keywordArr = keywords.split(",");
		System.out.println(keywordArr);		
		return customRepo.searchTag(keywordArr, entityType.name());
	}
	
	
	
}
