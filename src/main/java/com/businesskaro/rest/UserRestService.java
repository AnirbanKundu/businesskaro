package com.businesskaro.rest;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.entity.TblUserPassword;
import com.businesskaro.entity.repo.TblUserPasswordRepo;
import com.businesskaro.mail.NewUserEmailSender;
import com.businesskaro.model.BKException;
import com.businesskaro.model.BKUser;
import com.businesskaro.rest.dto.ResetPasswordRequest;
import com.businesskaro.security.EncryptionUtil;

@RestController
public class UserRestService extends BKRestService {
	
	Logger logger = Logger.getLogger(UserRestService.class.getName());
	
	
	@Autowired
	TblUserPasswordRepo userDao;
	
	@Autowired
	NewUserEmailSender email;

	@RequestMapping(value="/services/user" , method = RequestMethod.POST)
	public void createUser(@RequestBody BKUser user){
		logger.info("Create User for " + user);
		
		//String randomSalt = BKGuid.getNextGuid();
		
		List<TblUserPassword> list = userDao.findByUsrName(user.userName);
		if(list != null && list.size() > 0){
			throw new BKException("User Already Exist", "001", BKException.Type.USER_ALREADY_EXIST);
		}
		
		String rawPassword = user.password;
		String randomSalt =  EncryptionUtil.nextSessionId(); //Save this value in Database
		System.out.println("Raw password is :" + rawPassword);
		String encryptedPassword = EncryptionUtil.encode(rawPassword, randomSalt); //Save this value in DB along with the SALT
		
		user.password = encryptedPassword;
		user.randomSalt = randomSalt;
		
		TblUserPassword userPswd = new TblUserPassword();
		userPswd.setUsrEmail(user.email);
		userPswd.setUsrName(user.userName);
		userPswd.setUsrPassword(user.password);
		userPswd.setUsrSalt(user.randomSalt);
		userPswd.setCreateDt(new Date());
		userPswd.setLastUpd(new Date());
		
		userPswd = userDao.save(userPswd);
		System.out.println("****** Sending the email ******");
		try {
			email.send(user.email);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/services/user/resetPassword" , method = RequestMethod.POST)
	public void getResetPassword(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId, 
			ResetPasswordRequest request){
		
		List<TblUserPassword> userPswdEntitys = userDao.findByUsrName(request.email);
		if(userPswdEntitys.size() == 0){
			throw new BKException("UserNot Found", "", BKException.Type.USER_AUTH_FAIL );
		}
		
	}
	
	
	@RequestMapping(value="/services/user" , method = RequestMethod.GET)
	public BKUser getUser( 
			@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		
		logger.info("Secure Token " + secureToken);
		logger.info("Client Id  " + clientId);
		
		Integer userId = validateSecureToken(clientId, secureToken);
		
		logger.info("User Id  " + userId);
		
		TblUserPassword userPswd = userDao.findOne(userId);
		BKUser user = new BKUser();
		user.email = userPswd.getUsrEmail();
		user.id = userPswd.getUsrId();
		user.userName = userPswd.getUsrName();
	
		
		return user;  
	}
	
	
}
