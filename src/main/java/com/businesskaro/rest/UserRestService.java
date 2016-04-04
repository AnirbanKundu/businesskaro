package com.businesskaro.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.entity.TblUnregisteredUserPassword;
import com.businesskaro.entity.TblUserPassword;
import com.businesskaro.entity.repo.TblUnregisteredUserPasswordRepo;
import com.businesskaro.entity.repo.TblUserPasswordRepo;
import com.businesskaro.mail.NewUserEmailSender;
import com.businesskaro.model.BKException;
import com.businesskaro.model.BKUser;
import com.businesskaro.rest.dto.LoginResponse;
import com.businesskaro.rest.dto.ResetPasswordRequest;
import com.businesskaro.security.BKGuid;
import com.businesskaro.security.EncryptionUtil;
import com.businesskaro.security.SecureTokenUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserRestService extends BKRestService {
	
	Logger logger = Logger.getLogger(UserRestService.class.getName());
	
	@Autowired
	TblUserPasswordRepo userDao;
	
	@Autowired
	TblUnregisteredUserPasswordRepo unregisteredUserDao;
	
	@Autowired
	NewUserEmailSender email;
	
	@Autowired
	SecureTokenUtil secureTokenUtil;

	@RequestMapping(value="/services/uservalidate/{registeredToken}/email/{guid}" , method = RequestMethod.GET)
	public LoginResponse validateUser(@PathVariable("registeredToken") String emailToken,@PathVariable("guid") String guid) throws Exception{
		String registeredToken=null;		
		try
		{
			registeredToken = secureTokenUtil.validateEmailToken(guid, emailToken);	
			List<TblUnregisteredUserPassword> list = unregisteredUserDao.findByUsrSalt(registeredToken);
			if(list != null && list.size() < 0){
				throw new BKException("User Already Exist", "001", BKException.Type.USER_ALREADY_EXIST);
			}
			TblUserPassword userPswd = new TblUserPassword();	         
	    		userPswd.setUsrEmail(list.get(0).getUsrEmail());
	    		userPswd.setUsrName(list.get(0).getUsrName());
	    		userPswd.setUsrPassword(list.get(0).getUsrPassword());
	    		userPswd.setUsrSalt(list.get(0).getUsrSalt());
	    		userPswd.setCreateDt(new Date());
	    		userPswd.setLastUpd(new Date());
	    		userPswd = userDao.save(userPswd);
    		//Delete this record from temp table
   		 	//unregisteredUserDao.deleteUsersByUsrSalt(registeredToken);
	    	unregisteredUserDao.delete(list.get(0).getUsrId());	
    		String newGuid = BKGuid.getNextGuid();
			LoginResponse response = new LoginResponse();
			response.secureToken =  secureTokenUtil.generateSecurityToken(newGuid, userPswd.getUsrId());
			response.clientId = newGuid;
			response.profileCreated = userPswd.getProfileCreated();
			return response;
		 }
		 catch(Exception ex)
		 {
			 throw ex;
		 }		 
	}	
	
	
	/*
	 * This method is used for maintaining unregistered users
	 */
	
	
	@RequestMapping(value="/services/user" , method = RequestMethod.POST)
	public void registerNewUser(@RequestBody BKUser user,HttpServletRequest request){
		logger.info("Create User for "+user);
	
		//List<TblUnregisteredUserPassword> list = unregisteredUserDao.findByUsrName(user.userName);
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
		
		TblUnregisteredUserPassword userPswd = new TblUnregisteredUserPassword();
		userPswd.setUsrEmail(user.email);
		userPswd.setUsrName(user.userName);
		userPswd.setUsrPassword(user.password);
		userPswd.setUsrSalt(user.randomSalt);
		userPswd.setCreateDt(new Date());
		userPswd.setLastUpd(new Date());		
		userPswd = unregisteredUserDao.save(userPswd);		
		//System.out.println("*************User data saved successfully! *******");		
		//Encryption Process
		String newGuid = BKGuid.getNextGuid();
		String generateToken = null;
		try {
			generateToken = secureTokenUtil.generateEmailToken(newGuid, user.randomSalt);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		URL url=null;
		String serverUrl = null;
		try {
			url = new URL(request.getRequestURL().toString());
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	    if(url.getHost().compareToIgnoreCase("localhost")>-1){
	    	serverUrl = url.getProtocol() + "://" + url.getHost() +":" +url.getPort() ;
	    }
	    else{
	    	serverUrl = url.getProtocol() + "://" + url.getHost() ;  
	    }
		String activationURL= serverUrl + "/#/userprofile/validate/"+generateToken+"/email/"+newGuid;	
		try
		{
			email.sendEmailAndURL(user.email,activationURL);
			//System.out.println("*************Sending the email *******");
		}
		catch(Exception e)
		{
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
		
		Integer userId = validateSecureToken(secureTokenUtil,clientId, secureToken);
		
		logger.info("User Id  " + userId);
		
		TblUserPassword userPswd = userDao.findOne(userId);
		BKUser user = new BKUser();
		user.email = userPswd.getUsrEmail();
		user.id = userPswd.getUsrId();
		user.userName = userPswd.getUsrName();
		user.userType = userPswd.getUsrType();
	
		
		return user;  
	}
	
	
}
