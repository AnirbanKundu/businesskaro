package com.businesskaro.rest;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.entity.TblUserManagement;
import com.businesskaro.entity.TblUserPassword;
import com.businesskaro.entity.repo.TblUserManagementRepo;
import com.businesskaro.entity.repo.TblUserPasswordRepo;
import com.businesskaro.mail.ResetPasswordEmail;
import com.businesskaro.model.BKException;
import com.businesskaro.rest.dto.LoginRequest;
import com.businesskaro.rest.dto.LoginResponse;
import com.businesskaro.rest.dto.ResetPasswordRequest;
import com.businesskaro.security.BKGuid;
import com.businesskaro.security.EncryptionUtil;
import com.businesskaro.security.SecureTokenUtil;

@RestController
public class UserSecurityRestService extends BKRestService {

	Logger logger = Logger.getLogger(UserSecurityRestService.class.getName());
		
	@Autowired
	TblUserPasswordRepo userDao;
	
	@Autowired
	ResetPasswordEmail mailService;
	
	@Autowired
	SecureTokenUtil secureTokenUtil;
	
	@Autowired
	private TblUserManagementRepo managementRepo;
	

	@RequestMapping(value="/services/login" , method = RequestMethod.POST)
	public LoginResponse login(@RequestBody LoginRequest loginRequest ){
		
		logger.info("Security Request " + loginRequest.email);
		
		List<TblUserPassword> userPswdEntitys = userDao.findByUsrName(loginRequest.email);
		if(userPswdEntitys.size() == 0){
			throw new BKException("UserNot Found", "", BKException.Type.USER_AUTH_FAIL );
		}
		
		TblUserPassword userPswd = userPswdEntitys.get(0);
		String decryptedPassword = EncryptionUtil.decode(userPswd.getUsrPassword(), userPswd.getUsrSalt()); //Fetch the encrypted password and SALT from DB
		System.out.println("Decrypted password is :" + decryptedPassword);
		
		if(loginRequest.password.equals(decryptedPassword)){
		
			try {
				String newGuid = BKGuid.getNextGuid();
				LoginResponse response = new LoginResponse();
				response.secureToken =  secureTokenUtil.generateSecurityToken(newGuid, userPswd.getUsrId());
				response.clientId = newGuid;
				response.profileCreated = userPswd.getProfileCreated();
				
				return response;
			}  catch (Exception e) {
				throw new BKException("Unknown Error while generating secure token" , "000" , BKException.Type.INTERNAL_ERRROR);
			}
		}else{
			throw new BKException("Passwords do not match" , "401" , BKException.Type.USER_AUTH_FAIL);
		}
		
	}
	
	
	@RequestMapping(value="/services/logout" , method = RequestMethod.POST)
	public void logout(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId ){
	
		try{
			validateSecureToken(secureTokenUtil,clientId, secureToken);
		}catch(Exception ex){
			logger.info("token exception");
		}finally{
			secureTokenUtil.inValidateSecureToken(secureToken);
		}
	}
	
	@RequestMapping(value="/services/changePassword" , method = RequestMethod.POST)
	public LoginResponse changePassword(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId,@RequestBody LoginRequest loginRequest ){
	
		logger.info("Security Request " + loginRequest.email);
		
		Integer userId = validateSecureToken(secureTokenUtil,clientId, secureToken);
		TblUserManagement loggedInUser = managementRepo.findOne(userId);
		
		if(!loggedInUser.getUsrName().equalsIgnoreCase(loginRequest.email)){
			throw new BKException("Invalid User" , "403" , BKException.Type.IN_VALID_USER);
		}
		else{		
			List<TblUserPassword> userPswdEntitys = userDao.findByUsrName(loginRequest.email);
			if(userPswdEntitys.size() == 0){
				throw new BKException("UserNot Found", "", BKException.Type.USER_AUTH_FAIL );
			}
			
			TblUserPassword userPswd = userPswdEntitys.get(0);
			String decryptedPassword = EncryptionUtil.decode(userPswd.getUsrPassword(), userPswd.getUsrSalt()); //Fetch the encrypted password and SALT from DB
			System.out.println("Decrypted password is :" + decryptedPassword);
			
			if(loginRequest.password.equals(decryptedPassword)){
				String encryptedPassword = EncryptionUtil.encode(loginRequest.newPassword, userPswd.getUsrSalt()); //Save this value in DB along with the SALT
				userPswd.setUsrPassword(encryptedPassword);
				userPswd.setLastUpd(new Date());
				userDao.save(userPswd);
			}else{
				throw new BKException("Invalid User" , "400" , BKException.Type.IN_VALID_USER);
			}
		}
		
		return null;
	}
	
	//generate 10 random string
	//Update new password t the profile
	//use mail service send
	
	
	@RequestMapping(value="/services/resetPassword" , method = RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void resetPassword(@RequestBody ResetPasswordRequest loginRequest ){
		
		logger.info("Security Request " + loginRequest.email);
		
		List<TblUserPassword> userPswdEntitys = userDao.findByUsrName(loginRequest.email);
		if(userPswdEntitys.size() == 0){
			throw new BKException("UserNot Found", "", BKException.Type.USER_AUTH_FAIL );
		}
		
		TblUserPassword userPswd = userPswdEntitys.get(0);
		String newpswd = BKGuid.randomString(10);
		String encryptedPassword = EncryptionUtil.encode(newpswd, userPswd.getUsrSalt());
		userPswd.setUsrPassword(encryptedPassword);
		userDao.save(userPswd);
		
		try {
			mailService.send(loginRequest.email, newpswd);
		} catch (Exception e) {
			throw new BKException("Email Failed", "", BKException.Type.INTERNAL_ERRROR );
		}
		
	}
	
	
}
