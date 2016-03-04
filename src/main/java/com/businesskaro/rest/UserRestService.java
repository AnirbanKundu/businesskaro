package com.businesskaro.rest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.neo4j.cypher.internal.compiler.v2_1.perty.docbuilders.toStringDocBuilder;
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
import com.businesskaro.rest.dto.ResetPasswordRequest;
import com.businesskaro.security.EncryptionUtil;
import com.businesskaro.security.SecureTokenUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

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

	/*****
	 * REST END POINT TO CREATE NEW USER
	 *********/
	 public static Date addDays(Date date, int days)
	 {
		 Calendar cal = Calendar.getInstance();
	     cal.setTime(date);
	     cal.add(Calendar.DATE, days); //minus number would decrement the days
	     return cal.getTime();
	  }
	
	 /*
	  * I would like to test the URL is alive or not
	  * 
	  * Step 1: get the date(created_dt) from the table TblUnregisteredUserPassword using registeredToken
	  * 
	  * Step 2: add one day to the created_dt and stored it in expireddate variable
	  * 
	  * Step 3: Create a string variable called presentDate and initialize it the present date
	  * 
	  * Step 4: Next compare present date with expired date
	  * 
	  *      	if(date1.compareTo(date2)>0){
	        		System.out.println("Date1 is after Date2");
	        	}else if(date1.compareTo(date2)<0){
	        		System.out.println("Date1 is before Date2");
	        	}else if(date1.compareTo(date2)==0){
	        		System.out.println("Date1 is equal to Date2");
	        	}else{
	        		System.out.println("How to get here?");
	        	}
	        	
	  */
	@RequestMapping(value="/services/uservalidate/{registeredToken}" , method = RequestMethod.GET)
	public void validateUser(@PathVariable("registeredToken") String registeredToken){		
		System.out.println(registeredToken + " token received(From Local Database)");
		
		Date today = new Date();	
		SimpleDateFormat simpDate = new SimpleDateFormat("yyyy-MM-dd kk:mm");
		String todayString=simpDate.format(today);
		String createdDate=null;
		/*
		//Decryption process
		String decrpytedtext=EncryptionUtil._decodeDecrypt(registeredToken);
		String[] parts = decrpytedtext.split(":");
		String email = parts[0]; 
		String salt = parts[1];
		String date = parts[2]; 
		
		System.out.println("Part 1:"+email);
		System.out.println("Part 2:"+salt);
		System.out.println("Part 3:"+date);
		*/
		try
		{
		// STEP 1: GET USER DETAILS FROM TEMP TABLE - QUery the Temp table using registeredToken
		List<TblUnregisteredUserPassword> list = unregisteredUserDao.findByUsrSalt(registeredToken);
		 for( TblUnregisteredUserPassword e:list ){
	         System.out.print("Username :" + e.getUsrEmail( ));
	         System.out.println("\t User Password :" +e.getUsrPassword());
	         createdDate=e.getCreateDt().toString();
	         System.out.println("\t User Date :" +createdDate);
	      }		 
			//Applied parsing the date
		    Date presentDate = simpDate.parse(todayString);
		    Date entryDate = simpDate.parse(createdDate);
		    Date linkExpiredDate=addDays(entryDate, 1);
		     	
		    System.out.println("Present Date is :"+presentDate);
		    System.out.println("Entry Date is :"+entryDate);
		    System.out.println("Link Expire Date is :"+linkExpiredDate);
		     	
		    if(presentDate.compareTo(linkExpiredDate)>0){
		    	System.out.println("entryDate is after linkExpiredDate");
	        	System.out.println("The Activation Link expired! Please register again! Thanks!!");
	        }else if(presentDate.compareTo(linkExpiredDate)<0){
	        	System.out.println("entryDate is before linkExpiredDate");
	        	System.out.println("Activation Process completed!");
	        	//Insert data in main tble. Folllow the steps as in the creaTeUse method below
	        	TblUserPassword userPswd = new TblUserPassword();		
	        	for( TblUnregisteredUserPassword e:list ){	         
	        		userPswd.setUsrEmail(e.getUsrEmail());
	        		userPswd.setUsrName(e.getUsrName());
	        		userPswd.setUsrPassword(e.getUsrPassword());
	        		userPswd.setUsrSalt(e.getUsrSalt());
	        		userPswd.setCreateDt(new Date());
	        		userPswd.setLastUpd(new Date());
	        	}
	        	userPswd = userDao.save(userPswd);
	        }else if(presentDate.compareTo(linkExpiredDate)==0){
	        	System.out.println("entryDate is equal to linkExpiredDate");
	        }else{System.out.println("How to get here?");
	        }	 
		 }
		 catch(Exception ex)
		 {
			 System.out.println("**********Exception**********************");
			 System.out.println(ex.getMessage());
			 System.out.println("***********Exception********************");
		 }
		 
		 //Delete this record from temp table
		 //unregisteredUserDao.deleteUsersByUsrSalt(registeredToken);
	}	
	
	
	/*
	 * This method is used for maintaining unregistered users
	 */
	@RequestMapping(value="/services/uservalidate", method=RequestMethod.POST)
	public void creteUnRegisteredUser(@RequestBody BKUser user){
		logger.info("Create User for "+user);

		List<TblUnregisteredUserPassword> list = unregisteredUserDao.findByUsrName(user.userName);
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
		System.out.println("*************User data saved successfully! *******");
		/*
		 System.out.println("****** Sending the email ******");
		 
		try {
			email.send(user.email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		/* 
		 * We have to set server URL here
		 */
		String serverUrl = "http://localhost:8080/#/"; //Grab the domain of the caller. 
		String activationURL= serverUrl + "/userprofile/validate/"+user.randomSalt+"/email";//"http://localhost:8080/registeredUser/password/"+user.password;
		
		try
		{
			email.sendEmailAndURL(user.email,activationURL);
			System.out.println("*************Sending the email *******");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}				
	}
	
	
	@RequestMapping(value="/services/user" , method = RequestMethod.POST)
	public void createUser(@RequestBody BKUser user){
		logger.info("Create User for "+user);

		List<TblUnregisteredUserPassword> list = unregisteredUserDao.findByUsrName(user.userName);
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
		/*
		System.out.println("****** Sending the email ******");
		try {
			email.send(user.email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
		System.out.println("*************User data saved successfully! *******");
		
		//Encryption Process
		String text=user.email+":"+user.randomSalt+":"+userPswd.getCreateDt();
		String encodeAndEncryptedText=EncryptionUtil._encodeEncrypt(text,user.email);
		//String encodeAndEncryptedText=EncryptionUtil._encodeEncrypt(text,"Hello");
		//String encodeAndEncryptedText="abc%abc%";
		
		
		System.out.println("Text:"+text);
		System.out.println("Encrypted Text:"+encodeAndEncryptedText);
		
		
		/* 
		 * We have to set server URL here
		 */
		String serverUrl = "http://localhost:8080/#"; //Grab the domain of the caller. 
		String activationURL= serverUrl + "/userprofile/validate/"+user.randomSalt+"/email";//"http://localhost:8080/registeredUser/password/"+user.password;
	
		try
		{
			email.sendEmailAndURL(user.email,activationURL);
			System.out.println("*************Sending the email *******");
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
	
		
		return user;  
	}
	
	
}
