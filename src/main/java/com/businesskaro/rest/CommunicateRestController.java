package com.businesskaro.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.entity.TblEmailAudit;
import com.businesskaro.entity.TblUserManagement;
import com.businesskaro.entity.TblUserPassword;
import com.businesskaro.entity.repo.CustomRepository;
import com.businesskaro.entity.repo.TblEmailAuditRepo;
import com.businesskaro.entity.repo.TblUserManagementRepo;
import com.businesskaro.entity.repo.TblUserPasswordRepo;
import com.businesskaro.mail.CommunicateMail;
import com.businesskaro.model.AdminEntitySearch;
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
	
	@Autowired
	TblEmailAuditRepo emailAuditRepo;
	

	@Autowired
	TblUserManagementRepo managementRepo;

	

	
	@RequestMapping(value="/services/communicate" , method = RequestMethod.POST)
	public void sendMail(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId, @RequestBody CommunicateRequest request){		
		try{		
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");			
			String todayString=dateFormat.format(new Date());	
			Date todayDate=new Date();
			try {
				todayDate = dateFormat.parse(todayString);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
			Integer userId = validateSecureToken(secureTokenUtil, clientId, secureToken);
			TblUserPassword fromUserEmail = userDao.findOne(userId);
			
			request.toId = 2; // for the sake of testing. This needs to be removed.
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
			
			TblEmailAudit emailAudit = new TblEmailAudit();
			emailAudit.setEmailContent(request.message);
			emailAudit.setEmailSentDate(todayDate);
			emailAudit.setEmailSubject("Email sent for"+ " "+ request.entityType);
			emailAudit.setEntityId(Integer.parseInt(request.entityId));
			emailAudit.setEntityType(request.entityType);
			emailAudit.setSentFrom(userId);
			emailAudit.setSentFromEmail(fromUserEmail.getUsrEmail());
			emailAudit.setSentTo(request.toId);
			emailAudit.setSentToEmail(toUserEmail.getUsrEmail());
			//Written by nagendra--START			
			List<TblEmailAudit> list = emailAuditRepo.findAllBySentFromAndEmailSentDate(userId,todayDate);			
			if(list != null && list.size() < 0){
				throw new BKException("User Already Exist", "001", BKException.Type.USER_ALREADY_EXIST);
			}
			System.out.println("email limit:"+list.size());
			
			if(list.size()<=5)
			{
				try
				{
					mail.send(communicate);
					emailAuditRepo.save(emailAudit);
				}
				catch(Exception e){
					
				}
			}
			else
			{
				throw new BKException("Email sending limit exceeded" , "001" , BKException.Type.BUSSINESS_VALIDATION);
			}			
		} catch(BKException e){
			throw e;
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
	
	
	/* Written by Nagendra  START */
	/* 
	 * It takes either OFFER AND REQUEST and show the matches
	 * 
	 */
	
	@RequestMapping(value = "/manageEmail/{requestOfferType}", method = RequestMethod.GET)
	public List<TblEmailAudit> getAllEmails(
			@RequestHeader("SECURE_TOKEN") String secureToken,
			@RequestHeader("CLIENT_ID") String clientId,
			@PathVariable("requestOfferType") String entityType) {
		//@PathVariable("registeredToken") String emailToken
		// Integer userId = null;
		try {
			Integer userId = validateSecureToken(secureTokenUtil, clientId, secureToken);
			
			//System.out.println("Request Type you selected:"+requestOfferType);
			
			//TblEmailAudit emailAudit=emailAuditRepo.findOne(userId);
			TblUserManagement loggedInUser = managementRepo.findOne(userId);			
			
			 if(loggedInUser.getUsrType().equals("ADMIN")){ 	
				List<TblEmailAudit> emailMgmt = (List<TblEmailAudit>) emailAuditRepo.findAll();
				List<TblEmailAudit> toRemove = new ArrayList<TblEmailAudit>();
				
				for(TblEmailAudit a: emailMgmt){
					String entityback=a.getEntityType();
					System.out.println("Entity back:"+entityback);
					String et=entityType;
					System.out.println("Parameter:"+et);
				    if(!a.getEntityType().equals(entityType)){
				    	toRemove.add(a);
				    }
				}
				emailMgmt.removeAll(toRemove);				
				return emailMgmt;
				
			} else {
				throw new BKException("User not an admin", "403",
						BKException.Type.USER_AUTHENTICATION_FAIL);
			}
		} catch (BKException ex) {
			throw new BKException("User not an admin", "403",
					BKException.Type.USER_AUTHENTICATION_FAIL);
		}

	}

	/* Written by Nagendra END*/
}
