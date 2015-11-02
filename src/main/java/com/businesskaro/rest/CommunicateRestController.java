package com.businesskaro.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.entity.TblUserPassword;
import com.businesskaro.entity.repo.TblUserPasswordRepo;
import com.businesskaro.mail.CommunicateMail;
import com.businesskaro.model.BKException;
import com.businesskaro.model.BKUserProfileSummary;
import com.businesskaro.model.Communicate;
import com.businesskaro.model.ContactUs;
import com.businesskaro.rest.dto.CommunicateRequest;
import com.businesskaro.security.SecureTokenUtil;
import com.businesskaro.service.UserPersonalInfoService;

@RestController
public class CommunicateRestController extends BKRestService{

	@Autowired
	CommunicateMail mail;
	
	@Autowired
	TblUserPasswordRepo userDao;
	
	@Autowired
	UserPersonalInfoService service;
	
	@Autowired
	SecureTokenUtil secureTokenUtil;
	
	@RequestMapping(value="/services/communicate" , method = RequestMethod.POST)
	public void sendMail(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId, @RequestBody CommunicateRequest request){		
		try{
			Integer userId = validateSecureToken(secureTokenUtil, clientId, secureToken);
			TblUserPassword fromUserEmail = userDao.findOne(userId);
			TblUserPassword toUserEmail = userDao.findOne(request.toId);	
			BKUserProfileSummary fromUser = service.getUserPersonalSummary(userId);
			BKUserProfileSummary toUser = service.getUserPersonalSummary(request.toId);	
			Communicate communicate = new Communicate();
			communicate.entityId = request.entityId;
			communicate.entityType = request.entityType;
			communicate.message = request.message;
			communicate.fromEmailAddress = fromUserEmail.getUsrName();
			communicate.fromName = fromUser.firstName;
			communicate.toEmailAddress = toUserEmail.getUsrName();
			communicate.toName = toUser.firstName;			
			try {
				mail.send(communicate);
			} catch (Exception e) {
				throw new BKException("User Not Authorized" , "001" , BKException.Type.EXTERNAL_ERRROR);
			}
		} catch(Exception e){
			throw new BKException("User Not Authorized" , "001" , BKException.Type.INTERNAL_ERRROR);
		}
		
	}
	
	@RequestMapping(value="/services/contactus" , method = RequestMethod.POST)
	public void sendMail(@RequestBody ContactUs request){		
		try {
			mail.contactUs(request);
		} catch (Exception e) {
			throw new BKException("Error sending email" , "001" , BKException.Type.INTERNAL_ERRROR);
		}
	}
}
