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
import com.businesskaro.model.BKEntityType;
import com.businesskaro.model.TagEntityRequest;
import com.businesskaro.service.TagService;

@RestController
public class TagRestService {

	@Autowired
	TagService tagService;
	
	@RequestMapping(value="/services/tag" , method = RequestMethod.POST)
	public void createTag(@RequestBody TagEntityRequest request){
		tagService.createTagEntry(request);
	}
	
	@RequestMapping(value="/services/tag/names" , method = RequestMethod.GET)
	public List<Tag> getTAgNames(){
		return tagService.getTagNames();
	}
	
	@RequestMapping(value="/services/tag/entity" , method = RequestMethod.GET)
	public List<TagEntity> searchTag(@RequestParam("keyword") String keyword , @RequestParam("entityType") BKEntityType entityType ){
		if(entityType == BKEntityType.ALL){
			return tagService.searchForTagName(keyword);
		}else{
			return tagService.searchForTagNameAndEntityType(keyword, entityType.name());
		}
	}
	
}
