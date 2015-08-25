package com.businesskaro.rest.dto;

import com.businesskaro.model.BKEntityType;

public class TagEntityRequest {

	public Integer entityId;
	
	public BKEntityType entityType;
	
	public TagItem[] existingtags;
	
	public TagItem[] newTags;
}


