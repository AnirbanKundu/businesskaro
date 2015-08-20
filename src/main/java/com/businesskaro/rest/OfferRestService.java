package com.businesskaro.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.model.OfferRequest;
import com.businesskaro.model.OfferRequestEnum;
import com.businesskaro.service.OfferRequestService;

@RestController
public class OfferRestService {

	@Autowired
	OfferRequestService service;
	
	@RequestMapping(value="/services/offer" , method = RequestMethod.POST)
	public void createOffer(@RequestBody OfferRequest offer){
		service.createorUpdate(offer, OfferRequestEnum.OFFER);
	}
	
	@RequestMapping(value="/services/offer" , method = RequestMethod.PUT)
	public void updateOffer(@RequestBody OfferRequest offer){
		service.createorUpdate(offer, OfferRequestEnum.OFFER);
	}
	
	@RequestMapping(value="/services/offer/{userId}" , method = RequestMethod.GET)
	public List<OfferRequest> getOffers(@PathVariable("userId") Integer userId){
		return service.getAll(userId);
	}
	
	@RequestMapping(value="/services/offer/{offerId}" , method = RequestMethod.DELETE)
	public void deleteOffer(@PathVariable("offerId") Integer offerId){
		service.delete(offerId);
	}
	
}
