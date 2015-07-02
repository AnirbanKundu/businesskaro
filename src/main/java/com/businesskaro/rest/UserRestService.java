package com.businesskaro.rest;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.dao.BKUserDao;
import com.businesskaro.model.BKUser;

@RestController
public class UserRestService extends BKRestService {
	
	Logger logger = Logger.getLogger(UserRestService.class.getName());
	
	@Autowired
	BKUserDao dao;

	@RequestMapping(value="/services/user" , method = RequestMethod.POST)
	public void createUser(@RequestBody BKUser user){
		logger.info("Create User for " + user);
		dao.createUser(user);
	}
	
	
	@RequestMapping(value="/services/user/{userName}" , method = RequestMethod.GET)
	public BKUser getUser(@PathVariable String userName, 
			@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		logger.info("Get User for " + userName);
		logger.info("Secure Token " + secureToken);
		logger.info("Client Id  " + clientId);
		
		validateSecureToken(clientId, secureToken);
		
		return dao.retrieveBKUser(userName);
	}
	
	
}
