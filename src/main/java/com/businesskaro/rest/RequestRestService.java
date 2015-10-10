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

import com.businesskaro.model.BKException;
import com.businesskaro.model.OfferRequest;
import com.businesskaro.model.OfferRequestEnum;
import com.businesskaro.service.OfferRequestService;

@RestController
public class RequestRestService extends BKRestService{

	@Autowired
	OfferRequestService service;
	
	@RequestMapping(value="/services/request" , method = RequestMethod.POST)
	public Integer createRequest(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId,@RequestBody OfferRequest request){
		Integer userId = validateSecureToken(clientId, secureToken);
		request.userId = userId;
		request.createDate = new Date();
		request.updateDate = new Date();
		return service.createorUpdate(request,OfferRequestEnum.REQUEST);
	}
	
	@RequestMapping(value="/services/request" , method = RequestMethod.PUT)
	public void updateRequest(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId,@RequestBody OfferRequest request){
		Integer userId = validateSecureToken(clientId, secureToken);
		request.userId = userId;
		request.updateDate = new Date();
		service.createorUpdate(request,OfferRequestEnum.REQUEST);
	}
	
	@RequestMapping(value="/services/request" , method = RequestMethod.GET)
	public List<OfferRequest> getRequest(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		Integer userId = validateSecureToken(clientId, secureToken);
		return service.getAll(userId, OfferRequestEnum.REQUEST);
	}
	
	@RequestMapping(value="/services/request/{requestId}" , method = RequestMethod.DELETE)
	public void deleteRequest(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId,@PathVariable("requestId") Integer requestId){
		Integer userId = validateSecureToken(clientId, secureToken);
		if(userId!=null){
			service.delete(requestId);
		}
		
	}
	
	@RequestMapping(value="/services/request/summary/{requestId}" , method = RequestMethod.GET)
	public OfferRequest getRequestSummary(@PathVariable("requestId") Integer requestId){
		return service.getSummary(requestId);
	}
	
	@RequestMapping(value="/services/request/detail/{requestId}" , method = RequestMethod.GET)
	public OfferRequest getRequestDetails(@PathVariable("requestId") Integer requestId, @RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		try{
			Integer userId = validateSecureToken(clientId, secureToken);
			OfferRequest result = service.getDetails(requestId);
			
			if(result.userId!=userId){
				throw new BKException("User Not Authorized" , "001" , BKException.Type.INTERNAL_ERRROR);
			} else{
				return result;
			}
			 
		} catch(Exception e){
			throw new BKException("User Not Authorized" , "001" , BKException.Type.INTERNAL_ERRROR);
		}
	}
	
	@RequestMapping(value="/services/request/user/{userId}" , method = RequestMethod.GET)
	public List<OfferRequest> getRequestByUser(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId,@PathVariable("userId") Integer userId){
		try{
			validateSecureToken(clientId, secureToken);
			return service.getOfferByUserId(userId, OfferRequestEnum.OFFER);
		} catch(Exception e){
			throw new BKException("User Not Authorized" , "001" , BKException.Type.INTERNAL_ERRROR);
		}
        
	}

}
