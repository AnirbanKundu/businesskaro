package com.businesskaro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.businesskaro.entity.TblUsrReqOffer;
import com.businesskaro.entity.repo.TblUsrReqOfferRepo;
import com.businesskaro.model.OfferRequest;

@Component
public class OfferRequestService {

	@Autowired
	TblUsrReqOfferRepo reqOfferRepo;
	
	public void create(OfferRequest offer) {
		TblUsrReqOffer entity = new TblUsrReqOffer();
		
		
		reqOfferRepo.save(entity);
	}

	public void update(OfferRequest offer) {
		// TODO Auto-generated method stub
		
	}

	public List<OfferRequest> getAll(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(Integer offerId) {
		// TODO Auto-generated method stub
		
	}

}
