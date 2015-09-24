package com.businesskaro.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.entity.TblUserPassword;
import com.businesskaro.entity.repo.TblUserPasswordRepo;
import com.businesskaro.mail.CommunicateMail;
import com.businesskaro.model.Communicate;
import com.businesskaro.rest.dto.CommunicateRequest;

@RestController
public class CommunicateRestController extends BKRestService{

	@Autowired
	CommunicateMail mail;
	
	@Autowired
	TblUserPasswordRepo userDao;
	
	@RequestMapping(value="/services/communicate" , method = RequestMethod.POST)
	public void sendMail(@RequestBody CommunicateRequest request ){
		TblUserPassword fromUser = userDao.findOne(request.fromId);
		TblUserPassword toUser = userDao.findOne(request.toId);
		
		Communicate communicate = new Communicate();
		communicate.entityId = request.entityId;
		communicate.entityType = request.entityType;
		communicate.message = request.message;
		communicate.fromEmailAddress = fromUser.getUsrEmail();
		communicate.fromName = fromUser.getUsrName();
		communicate.toEmailAddress = toUser.getUsrEmail();
		communicate.toName = toUser.getUsrName();
		
		try {
			mail.send(communicate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
