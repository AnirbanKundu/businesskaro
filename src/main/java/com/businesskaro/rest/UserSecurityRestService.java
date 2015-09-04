package com.businesskaro.rest;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.entity.TblUserPassword;
import com.businesskaro.entity.repo.TblUserPasswordRepo;
import com.businesskaro.mail.Mail;
import com.businesskaro.model.BKException;
import com.businesskaro.rest.dto.LoginRequest;
import com.businesskaro.rest.dto.LoginResponse;
import com.businesskaro.security.BKGuid;
import com.businesskaro.security.EncryptionUtil;
import com.businesskaro.security.SecureTokenUtil;

@RestController
public class UserSecurityRestService {

	Logger logger = Logger.getLogger(UserSecurityRestService.class.getName());
		
	@Autowired
	TblUserPasswordRepo userDao;
	
	@Autowired
	Mail mailService;
	

	@RequestMapping(value="/services/login" , method = RequestMethod.POST)
	public LoginResponse login(@RequestBody LoginRequest loginRequest ){
		
		logger.info("Security Request " + loginRequest.userName);
		
		List<TblUserPassword> userPswdEntitys = userDao.findByUsrName(loginRequest.userName);
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
				response.secureToken =  SecureTokenUtil.generateSecurityToken(newGuid, userPswd.getUsrId());
				response.clientId = newGuid;
				return response;
			}  catch (Exception e) {
				throw new BKException("Unknown Error while generating secure token" , "000" , BKException.Type.INTERNAL_ERRROR);
			}
		}else{
			throw new BKException("Invalid User" , "000" , BKException.Type.IN_VALID_USER);
		}
		
	}
	
	//generate 10 random string
	//Update new password t the profile
	//use mail service send
	
	
	@RequestMapping(value="/services/resetPassword" , method = RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void resetPassword(@RequestBody LoginRequest loginRequest ){
		
		logger.info("Security Request " + loginRequest.userName);
		
		List<TblUserPassword> userPswdEntitys = userDao.findByUsrName(loginRequest.userName);
		if(userPswdEntitys.size() == 0){
			throw new BKException("UserNot Found", "", BKException.Type.USER_AUTH_FAIL );
		}
		
		TblUserPassword userPswd = userPswdEntitys.get(0);
		String newpswd = BKGuid.randomString(10);
		String encryptedPassword = EncryptionUtil.encode(newpswd, userPswd.getUsrSalt());
		userPswd.setUsrPassword(encryptedPassword);
		userDao.save(userPswd);
		
		mailService.sendMailx("", userPswd.getUsrEmail(), " Please use the password "+ newpswd +"  to login. Please dont forgot to change the password ");
		
	}
	
	
}
