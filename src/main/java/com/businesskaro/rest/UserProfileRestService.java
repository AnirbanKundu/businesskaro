package com.businesskaro.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.model.BKUserProfileSummary;
import com.businesskaro.model.BkUserProfile;
import com.businesskaro.service.UserPersonalInfoService;

@RestController
public class UserProfileRestService {
	
	@Autowired
	UserPersonalInfoService service;

	@RequestMapping(value="/services/userProfile" , method = RequestMethod.POST)
	public BkUserProfile createUserProfile(@RequestBody BkUserProfile bkUserProfile){
		return service.createOrUpdateUserPersonalInfo(bkUserProfile, true);
	}
	
	@RequestMapping(value="/services/userProfile" , method = RequestMethod.PUT)
	public BkUserProfile updateUserProfile(@RequestBody BkUserProfile bkUserProfile){
		return service.createOrUpdateUserPersonalInfo(bkUserProfile, false);
	}
	
	@RequestMapping(value="/services/userProfile/{userId}" , method = RequestMethod.GET)
	public BkUserProfile getUserProfile(@PathVariable("userId") Integer userId){
		return service.getUserPersonalInfo(userId);
	}
	
	@RequestMapping(value="/services/userProfile/{userId}/summary" , method = RequestMethod.GET)
	public BKUserProfileSummary getUserProfileSummary(@PathVariable("userId") Integer userId){
		return service.getUserPersonalSummary(userId);
	}
	
	@RequestMapping(value="/services/userProfile/{userId}/details" , method = RequestMethod.GET)
	public BkUserProfile getUserProfileDetails(@PathVariable("userId") Integer userId){
		return service.getUserPersonalInfo(userId);
	}
	
}
