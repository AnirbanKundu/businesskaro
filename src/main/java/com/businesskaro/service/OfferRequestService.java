package com.businesskaro.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.businesskaro.entity.BrgUsrReqOfferQuestion;
import com.businesskaro.entity.BrgUsrReqrIndustry;
import com.businesskaro.entity.BrgUsrReqrState;
import com.businesskaro.entity.LkpIndustry;
import com.businesskaro.entity.LkpState;
import com.businesskaro.entity.TblUsrReqOffer;
import com.businesskaro.entity.UserPersonalInfoSummary;
import com.businesskaro.entity.repo.BrgUsrReqOfferQuestionRepo;
import com.businesskaro.entity.repo.BrgUsrReqrIndustryRepo;
import com.businesskaro.entity.repo.BrgUsrReqrStateRepo;
import com.businesskaro.entity.repo.StateRepo;
import com.businesskaro.entity.repo.TblUsrReqOfferRepo;
import com.businesskaro.entity.repo.UserInductryRepo;
import com.businesskaro.entity.repo.UserPersonalInfoSummaryRepo;
import com.businesskaro.model.OfferRequest;
import com.businesskaro.model.OfferRequestEnum;
import com.businesskaro.model.Questions;

@Component
public class OfferRequestService {

	@Autowired
	TblUsrReqOfferRepo reqOfferRepo;
	
	@Autowired
	BrgUsrReqOfferQuestionRepo questionRepo;
	
	@Autowired
	UserInductryRepo userIndRepo;
	
	@Autowired
	BrgUsrReqrIndustryRepo bgrUsrReqIndustryRepo;
	
	@Autowired
	BrgUsrReqrStateRepo brgUsrReqState;
	
	@Autowired
	StateRepo stateRepo;
	
	@Autowired
	UserPersonalInfoSummaryRepo userInfoSummary;
	
	@Transactional
	public Integer createorUpdate(OfferRequest model, OfferRequestEnum type) {
		
		TblUsrReqOffer entity = new TblUsrReqOffer();
		
		if(model.id!=null){
			entity.setReqOffrId(model.id);
		}		
		entity.setReqOffrTyp(type.toString());
		entity.setReqOffrTitle(model.title);
		entity.setReqOffrDesc(model.description);
		entity.setTargAudienceId(model.intdAudience);
		entity.setCreateDt(model.createDate);
		entity.setLastUpd(model.updateDate);
		
		// -- FOR REQUEST 
		if(model.imgUrl!=null){
			entity.setImageUrl(model.imgUrl);
		}
		if(model.isVerified){
			entity.setIsVerified(1);
		} else{
			entity.setIsVerified(0);
		}
		
		UserPersonalInfoSummary userInfo = userInfoSummary.findOne(model.userId);
		entity.setTblUserPersInfoSumry(userInfo);
		
		entity = reqOfferRepo.save(entity);
		
		if(model.trgtIndustry!=null){
			for (Integer indusId : model.trgtIndustry) {
				LkpIndustry indEntity = userIndRepo.findOne(indusId);
				if (indEntity != null) {
					BrgUsrReqrIndustry industry = new BrgUsrReqrIndustry();
					industry.setLkpIndustry(indEntity);
					industry.setTblUsrReqOffer(entity);
					bgrUsrReqIndustryRepo.save(industry);
				}
			}
		}
		
		if(model.trgtLocation!=null){
			for(Integer stateId: model.trgtLocation){
				LkpState stateLkpEntity = stateRepo.findOne(stateId);
				if(stateLkpEntity!=null){
					BrgUsrReqrState state =  new BrgUsrReqrState();
					state.setLkpState(stateLkpEntity);
					state.setTblUsrReqOffer(entity);
					brgUsrReqState.save(state);
				}
			}
		}
		return entity.getReqOffrId();
	}

	public List<OfferRequest> getAll(Integer userId, OfferRequestEnum offer) {
		
		Iterable<TblUsrReqOffer> usrObjs = reqOfferRepo.findAllByTblUserPersInfoSumry(userInfoSummary.findOne(userId));
		
		List<OfferRequest> result = new ArrayList<OfferRequest>();
		for(TblUsrReqOffer offerReq : usrObjs ){
			if(offerReq.getReqOffrTyp().equalsIgnoreCase(offer.name())){
				result.add(mapper(offerReq));
			}
		}
		return result;
	}

	public void delete(Integer offerId) {
		reqOfferRepo.delete(offerId);
		
	}
	
	private OfferRequest mapper(TblUsrReqOffer fromTable){
		OfferRequest result = new OfferRequest();
		result.id = fromTable.getReqOffrId();
		result.description = fromTable.getReqOffrDesc();
		result.title = fromTable.getReqOffrTitle();
		result.intdAudience = fromTable.getTargAudienceId();
		int[] trgtIndustryIds = new int[fromTable.getBrgUsrReqrIndustries().size()];
		for(int i=0;i<fromTable.getBrgUsrReqrIndustries().size();i++){
			trgtIndustryIds[i] = fromTable.getBrgUsrReqrIndustries().get(i).getLkpIndustry().getIndustryId();
		}
		result.trgtIndustry = trgtIndustryIds;
		
		int[] usrStatesIds = new int[fromTable.getBrgUsrReqrStates().size()];
		for(int j=0;j<fromTable.getBrgUsrReqrStates().size();j++){
			usrStatesIds[j] = fromTable.getBrgUsrReqrStates().get(j).getLkpState().getStateId();
		}
		result.trgtLocation = usrStatesIds;
		
		List<Questions> questions = new ArrayList<Questions>();
		for(BrgUsrReqOfferQuestion quest:fromTable.getBrgUsrReqOfferQuestions()){
			Questions question = new Questions();
			question.questionId = quest.getLkpQuestion().getQuestId();
			question.response = quest.getLkpQuestion().getResponseTyp();
		}
		result.questionList = questions;
		result.imgUrl = fromTable.getImageUrl();
		if(fromTable.getTblUserPersInfoSumry() != null){
			result.userId = fromTable.getTblUserPersInfoSumry().getUsrId();
		}
		result.createDate = fromTable.getCreateDt();
		result.updateDate = fromTable.getLastUpd();
		return result;
	}
	
	private OfferRequest mapperSummary(TblUsrReqOffer fromTable){
		OfferRequest result = new OfferRequest();
		result.id = fromTable.getReqOffrId();
		result.description = fromTable.getReqOffrDesc();
		result.title = fromTable.getReqOffrTitle();
		result.intdAudience = fromTable.getTargAudienceId();
		
		int[] usrStatesIds = new int[fromTable.getBrgUsrReqrStates().size()];
		for(int j=0;j<fromTable.getBrgUsrReqrStates().size();j++){
			usrStatesIds[j] = fromTable.getBrgUsrReqrStates().get(j).getReqrIndus();
		}
		result.trgtLocation = usrStatesIds;
		
		// Change imageURL to send the thumb nail
		if(fromTable.getImageUrl()!=null){
			String url = fromTable.getImageUrl();
			String[] splitted = url.split("upload/");
			String summaryUrl = splitted[0]+"upload/t_media_lib_thumb/"+splitted[1];
			result.imgUrl = summaryUrl;
		}
		
		
		result.userId = fromTable.getTblUserPersInfoSumry().getUsrId();
		result.createDate = fromTable.getCreateDt();
		result.updateDate = fromTable.getLastUpd();
		return result;
	}
	
	public OfferRequest getSummary(Integer offerId){
		return mapperSummary(reqOfferRepo.findOne(offerId)); 
	}
	
	public OfferRequest getDetails(Integer offerId){
		return mapper(reqOfferRepo.findOne(offerId)); 
	}
}
