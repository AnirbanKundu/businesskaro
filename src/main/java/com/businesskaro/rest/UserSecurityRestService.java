package com.businesskaro.rest;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.dao.BKUserDao;
import com.businesskaro.model.BKException;
import com.businesskaro.model.BKUser;
import com.businesskaro.rest.dto.LoginRequest;
import com.businesskaro.rest.dto.LoginResponse;
import com.businesskaro.security.BKGuid;
import com.businesskaro.security.EncryptionUtil;
import com.businesskaro.security.SecureTokenUtil;

@RestController
public class UserSecurityRestService {

	Logger logger = Logger.getLogger(UserSecurityRestService.class.getName());
	
	
	@Autowired
	BKUserDao dao;

	@RequestMapping(value="/services/login" , method = RequestMethod.POST)
	public LoginResponse login(@RequestBody LoginRequest loginRequest ){
		
		logger.info("Security Request " + loginRequest.userName);
		
		
		BKUser user = dao.retrieveBKUser(loginRequest.userName);
		
		String decryptedPassword = EncryptionUtil.decode(user.password, user.randomSalt); //Fetch the encrypted password and SALT from DB
		System.out.println("Decrypted password is :" + decryptedPassword);
		
		if(loginRequest.password.equals(decryptedPassword)){
		
			try {
				String newGuid = BKGuid.getNextGuid();
				LoginResponse response = new LoginResponse();
				response.secureToken =  SecureTokenUtil.generateSecurityToken(newGuid, user.id);
				response.clientId = newGuid;
				return response;
			}  catch (Exception e) {
				throw new BKException("Unknown Error while generating secure token" , "000" , BKException.Type.INTERNAL_ERRROR);
			}
		}else{
			throw new BKException("Invalid User" , "000" , BKException.Type.IN_VALID_USER);
		}
		
	}
	
}
