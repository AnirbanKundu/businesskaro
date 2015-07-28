package com.businesskaro.rest;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.dao.BKUserDao;
import com.businesskaro.model.BKUser;
import com.businesskaro.security.EncryptionUtil;

@RestController
public class UserRestService extends BKRestService {
	
	Logger logger = Logger.getLogger(UserRestService.class.getName());
	
	@Autowired
	BKUserDao dao;

	@RequestMapping(value="/services/user" , method = RequestMethod.POST)
	public void createUser(@RequestBody BKUser user){
		logger.info("Create User for " + user);
		
		//String randomSalt = BKGuid.getNextGuid();
		
		String rawPassword = user.password;
		String randomSalt =  EncryptionUtil.nextSessionId(); //Save this value in Database
		System.out.println("Raw password is :" + rawPassword);
		String encryptedPassword = EncryptionUtil.encode(rawPassword, randomSalt); //Save this value in DB along with the SALT
		
		user.password = encryptedPassword;
		user.randomSalt = randomSalt;
		
		dao.createUser(user);
	}
	
	
	@RequestMapping(value="/services/user" , method = RequestMethod.GET)
	public BKUser getUser( 
			@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		
		logger.info("Secure Token " + secureToken);
		logger.info("Client Id  " + clientId);
		
		Integer userId = validateSecureToken(clientId, secureToken);
		
		logger.info("User Id  " + userId);
		
		return dao.retrieveBKId(userId);
	}
	
	
}
