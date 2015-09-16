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

import com.businesskaro.model.OfferRequest;
import com.businesskaro.model.OfferRequestEnum;
import com.businesskaro.service.OfferRequestService;

@RestController
public class OfferRestService extends BKRestService{

	@Autowired
	OfferRequestService service;
	
	@RequestMapping(value="/services/offer" , method = RequestMethod.POST)
	public Integer createOffer(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId, @RequestBody OfferRequest offer){
		Integer userId = validateSecureToken(clientId, secureToken);
		offer.userId = userId;
		offer.createDate = new Date();
		offer.updateDate = new Date();
		return service.createorUpdate(offer, OfferRequestEnum.OFFER);
	}
	
	@RequestMapping(value="/services/offer" , method = RequestMethod.PUT)
	public void updateOffer(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId, @RequestBody OfferRequest offer){
		Integer userId = validateSecureToken(clientId, secureToken);
		offer.userId = userId;
		offer.updateDate = new Date();
		
		service.createorUpdate(offer, OfferRequestEnum.OFFER);
	}
	
	@RequestMapping(value="/services/offer" , method = RequestMethod.GET)
	public List<OfferRequest> getOffers(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		Integer userId = validateSecureToken(clientId, secureToken);
		return service.getAll(userId, OfferRequestEnum.OFFER);
	}
	
	@RequestMapping(value="/services/offer/summary/{offerId}" , method = RequestMethod.GET)
	public OfferRequest getOfferSummary(@PathVariable("offerId") Integer offerId){
		return service.getSummary(offerId);
	}
	
	@RequestMapping(value="/services/offer/detail/{offerId}" , method = RequestMethod.GET)
	public OfferRequest getOfferDetails(@PathVariable("offerId") Integer offerId, @RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		return service.getDetails(offerId);
	}
	
	@RequestMapping(value="/services/offer/{offerId}" , method = RequestMethod.DELETE)
	public void deleteOffer(@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId,@PathVariable("offerId") Integer offerId){
		Integer userId = validateSecureToken(clientId, secureToken);
		if(userId!=null){
			service.delete(offerId);
		}
		
	}
	
}
