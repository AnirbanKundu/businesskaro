package com.businesskaro.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.model.BkUserProfile;

@RestController
public class UserProfileRestService {

	@RequestMapping(value="/services/userProfile" , method = RequestMethod.POST)
	public BkUserProfile createUserProfile(BkUserProfile bkUserProfile){
		return null;
	}
	
	@RequestMapping(value="/services/userProfile" , method = RequestMethod.PUT)
	public BkUserProfile updateUserProfile(BkUserProfile bkUserProfile){
		return null;
	}
	
	@RequestMapping(value="/services/userProfile/{userId}" , method = RequestMethod.PUT)
	public BkUserProfile getUserProfile(Long userId){
		return null;
	}
	
}
