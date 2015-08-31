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
public class RequestRestService {

	@Autowired
	OfferRequestService service;
	
	@RequestMapping(value="/services/request" , method = RequestMethod.POST)
	public void createRequest(@RequestBody OfferRequest request){
		service.createorUpdate(request,OfferRequestEnum.REQUEST);
	}
	
	@RequestMapping(value="/services/request" , method = RequestMethod.PUT)
	public void updateRequest(@RequestBody OfferRequest request){
		service.createorUpdate(request,OfferRequestEnum.REQUEST);
	}
	
	@RequestMapping(value="/services/request/{userId}" , method = RequestMethod.GET)
	public List<OfferRequest> getRequest(@PathVariable("userId") Integer userId){
		return service.getAll(userId);
	}
	
	@RequestMapping(value="/services/request/{requestId}" , method = RequestMethod.DELETE)
	public void deleteRequest(@PathVariable("requestId") Integer requestId){
		service.delete(requestId);
	}
	
	@RequestMapping(value="/services/request/summary/{requestId}" , method = RequestMethod.GET)
	public OfferRequest getRequestSummary(@PathVariable("requestId") Integer requestId){
		return service.getSumary(requestId);
	}
}
