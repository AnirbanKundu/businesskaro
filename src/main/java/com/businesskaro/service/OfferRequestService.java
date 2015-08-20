package com.businesskaro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.businesskaro.entity.BrgUsrIndustry;
import com.businesskaro.entity.BrgUsrReqOfferQuestion;
import com.businesskaro.entity.LkpIndustry;
import com.businesskaro.entity.TblUsrReqOffer;
import com.businesskaro.entity.repo.BrgUsrReqOfferQuestionRepo;
import com.businesskaro.entity.repo.TblUsrReqOfferRepo;
import com.businesskaro.entity.repo.UserInductryRepo;
import com.businesskaro.model.OfferRequest;
import com.businesskaro.model.OfferRequestEnum;

@Component
public class OfferRequestService {

	@Autowired
	TblUsrReqOfferRepo reqOfferRepo;
	
	@Autowired
	BrgUsrReqOfferQuestionRepo questionRepo;
	
	@Autowired
	UserInductryRepo userIndRepo;
	
	public void createorUpdate(OfferRequest model, OfferRequestEnum type) {
		
		TblUsrReqOffer entity = new TblUsrReqOffer();
		entity.setReqOffrTyp(type.toString());
		entity.setReqOffrTitle(model.title);
		//entity.setCompanyName(model.compName);
		entity.setReqOffrDesc(model.description);
		//entity.setBrgUsrReqrIndustries(model.trgtIndustry);
		//entity.setTargAudienceId(offer.trgtIndustry);
		//entity.setBrgUsrReqrStates(model.trgtLocation);
		entity.setCreateDt(model.createDate);
		entity.setLastUpd(model.updateDate);
		
		// -- FOR REQUEST 
		entity.setImageUrl(model.imgUrl);
		//entity.setIsVerified(isVerified);
		//entity.setTblUserPersInfoSumry(tblUserPersInfoSumry);
		
		TblUsrReqOffer offerReq = reqOfferRepo.save(entity);
		
		if(model.trgtIndustry!=null){
			for (Integer indusId : model.trgtIndustry) {
				LkpIndustry indEntity = userIndRepo.findOne(indusId);
				if (indEntity != null) {
					BrgUsrIndustry usrIndustry = new BrgUsrIndustry();
					usrIndustry.setLkpIndustry(indEntity);
				//	usrIndustry.setTblUserPersInfoSumry(summaryEntiy);
				//	brgUsrIndustryRepo.save(usrIndustry);
				}
			}
		}
		
	}

	public List<OfferRequest> getAll(Integer userId) {
		List<TblUsrReqOffer> result = reqOfferRepo.findAllByUserId(userId);
		
		
		return null;
	}

	public void delete(Integer offerId) {
		reqOfferRepo.delete(offerId);
		
	}
	
	private OfferRequest mapper(TblUsrReqOffer fromTable){
		OfferRequest result = new OfferRequest();
		//result.compName = fromTable.get
		result.description = fromTable.getReqOffrDesc();
		result.title = fromTable.getReqOffrTitle();
		//result.intdAudience = fromTable.getTargAudienceId();
		//result.trgtIndustry = fromTable.getBrgUsrReqrIndustries().get(0).getLkpIndustry().getIndustryName();
		//result.trgtLocation = fromTable.getBrgUsrReqrStates().get(0).getLkpState().getStateName();
		//result.questionList = fromTable.getBrgUsrReqOfferQuestions().get(0).getLkpQuestion();
		
		return result;
	}

}
