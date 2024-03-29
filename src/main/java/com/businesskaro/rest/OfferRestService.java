package com.businesskaro.rest;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.entity.TblUserManagement;
import com.businesskaro.entity.repo.TblUserManagementRepo;
import com.businesskaro.model.BKException;
import com.businesskaro.model.OfferRequest;
import com.businesskaro.model.OfferRequestEnum;
import com.businesskaro.security.SecureTokenUtil;
import com.businesskaro.service.OfferRequestService;

@RestController
public class OfferRestService extends BKRestService{

	@Autowired
	private TblUserManagementRepo managementRepo;
	
	@Autowired
	OfferRequestService service;
	
	@Autowired
	SecureTokenUtil secureTokenUtil;
	
	@RequestMapping(value="/services/offer" , method = RequestMethod.POST)
	public Integer createOffer(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId, @RequestBody OfferRequest offer){
		Integer userId = validateSecureToken(secureTokenUtil,clientId, secureToken);
		offer.userId = userId;
		offer.createDate = new Date();
		offer.updateDate = new Date();
		return service.createorUpdate(offer, OfferRequestEnum.OFFER);
	}
	
	@RequestMapping(value="/services/offer" , method = RequestMethod.PUT)
	public void updateOffer(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId, @RequestBody OfferRequest offer){
		Integer userId = validateSecureToken(secureTokenUtil,clientId, secureToken);
		if(offer.userId==0){
			offer.userId = userId;
		}
		
		offer.updateDate = new Date();
		
		service.createorUpdate(offer, OfferRequestEnum.OFFER);
	}
	
	@RequestMapping(value="/services/offer" , method = RequestMethod.GET)
	public List<OfferRequest> getOffers(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		Integer userId = validateSecureToken(secureTokenUtil,clientId, secureToken);
		return service.getAll(userId, OfferRequestEnum.OFFER);
	}
	
	@RequestMapping(value="/services/offer/summary/{offerId}" , method = RequestMethod.GET)
	public OfferRequest getOfferSummary(@PathVariable("offerId") Integer offerId){
		
		return service.getSummary(offerId);
	}
	
	@RequestMapping(value="/services/offer/detail/{offerId}" , method = RequestMethod.GET)
	public OfferRequest getOfferDetails(@PathVariable("offerId") Integer offerId, @RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){		
		try{
			Integer userId = validateSecureToken(secureTokenUtil,clientId, secureToken);
			OfferRequest result = service.getDetails(offerId);
			return result;
		} catch(Exception e){
			throw new BKException("User Not Authorized" , "001" , BKException.Type.INTERNAL_ERRROR);
		}
        
	}
	
	@RequestMapping(value="/services/offer/detailinedit/{offerId}" , method = RequestMethod.GET)
	public OfferRequest getOfferDetailsInEdit(@PathVariable("offerId") Integer offerId, @RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){	
		try{
			Integer userId = validateSecureToken(secureTokenUtil,clientId, secureToken);
			OfferRequest result = service.getDetails(offerId);
			TblUserManagement loggedInUser = managementRepo.findOne(userId);	
			boolean userAuthorized = false;
			if(result.userId==userId){
				userAuthorized = true;
			}			
			if(loggedInUser.getUsrType().equals("ADMIN")){
				userAuthorized = true;
			}			
			if(!userAuthorized ){
				throw new BKException("User Not Authorized" , "001" , BKException.Type.INTERNAL_ERRROR);
			} else{
				return result;
			}
			 
		} catch(Exception e){
			throw new BKException("User Not Authorized" , "001" , BKException.Type.INTERNAL_ERRROR);
		}
        
	}
	
	@RequestMapping(value="/services/offer/{offerId}" , method = RequestMethod.DELETE)
	public void deleteOffer(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId,@PathVariable("offerId") Integer offerId){
		Integer userId = validateSecureToken(secureTokenUtil,clientId, secureToken);
		if(userId!=null){
			service.delete(offerId);
		}
		
	}
	
	@RequestMapping(value="/services/offer/user/{userId}" , method = RequestMethod.GET)
	public List<OfferRequest> getOffersByUser(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId,@PathVariable("userId") Integer userId){
		try{
			validateSecureToken(secureTokenUtil,clientId, secureToken);
			return service.getOfferByUserId(userId, OfferRequestEnum.OFFER);
		} catch(Exception e){
			throw new BKException("User Not Authorized" , "001" , BKException.Type.INTERNAL_ERRROR);
		}
        
	}

	
}
