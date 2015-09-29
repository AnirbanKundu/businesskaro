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
import com.businesskaro.model.Communicate;
import com.businesskaro.rest.dto.CommunicateRequest;

@RestController
public class CommunicateRestController extends BKRestService{

	@Autowired
	CommunicateMail mail;
	
	@Autowired
	TblUserPasswordRepo userDao;
	
	@RequestMapping(value="/services/communicate" , method = RequestMethod.POST)
	public void sendMail(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId, @RequestBody CommunicateRequest request){		
		try{
			Integer userId = validateSecureToken(clientId, secureToken);
			TblUserPassword fromUser = userDao.findOne(userId);
			TblUserPassword toUser = userDao.findOne(request.toId);			
			Communicate communicate = new Communicate();
			communicate.entityId = request.entityId;
			communicate.entityType = request.entityType;
			communicate.message = request.message;
			communicate.fromEmailAddress = fromUser.getUsrName();
			communicate.fromName = fromUser.getUsrName();
			communicate.toEmailAddress = toUser.getUsrName();
			communicate.toName = toUser.getUsrName();			
			try {
				mail.send(communicate);
			} catch (Exception e) {
				throw new BKException("User Not Authorized" , "001" , BKException.Type.EXTERNAL_ERRROR);
			}
		} catch(Exception e){
			throw new BKException("User Not Authorized" , "001" , BKException.Type.INTERNAL_ERRROR);
		}
		
	}
}
