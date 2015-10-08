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
import com.businesskaro.entity.LkpQuestion;
import com.businesskaro.entity.LkpState;
import com.businesskaro.entity.TblUsrReqOffer;
import com.businesskaro.entity.UserPersonalInfoSummary;
import com.businesskaro.entity.repo.BrgUsrReqOfferQuestionRepo;
import com.businesskaro.entity.repo.BrgUsrReqrIndustryRepo;
import com.businesskaro.entity.repo.BrgUsrReqrStateRepo;
import com.businesskaro.entity.repo.QuestionRepo;
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
	BrgUsrReqOfferQuestionRepo brgQuestionRepo;
	
	@Autowired
	QuestionRepo questionRepo;
	
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
		
		List<BrgUsrReqrIndustry> result = bgrUsrReqIndustryRepo.findByTblUsrReqOffer(entity);
		if(result.size()>0){
			bgrUsrReqIndustryRepo.delete(result);
		}
		
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
		
		List<BrgUsrReqrState> result1 = brgUsrReqState.findByTblUsrReqOffer(entity);
		if(result1.size()>0){
			brgUsrReqState.delete(result1);
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
		
		List<BrgUsrReqOfferQuestion> qaResult = brgQuestionRepo.findByTblUsrReqOffer(entity);
		if(qaResult.size()>0){
			brgQuestionRepo.delete(qaResult);
		}
		
		if(model.questionList!=null){
			for(Questions q : model.questionList){
				LkpQuestion questionLkpEntity = questionRepo.findOne(q.questionId);
				if(questionLkpEntity!=null){
					BrgUsrReqOfferQuestion qa = new BrgUsrReqOfferQuestion();
					qa.setLkpQuestion(questionLkpEntity);
					qa.setResponse(q.response);
					qa.setTblUsrReqOffer(entity);
					brgQuestionRepo.save(qa);
				}
			}
		}
		
		return entity.getReqOffrId();
	}

	public List<OfferRequest> getAll(Integer userId, OfferRequestEnum offer) {
		UserPersonalInfoSummary userSummary = userInfoSummary.findOne(userId);
		
		Iterable<TblUsrReqOffer> usrObjs = reqOfferRepo.findAllByTblUserPersInfoSumryAndReqOffrTyp(userSummary,offer.name());
		
		List<OfferRequest> result = new ArrayList<OfferRequest>();
		for(TblUsrReqOffer offerReq : usrObjs ){
			result.add(mapperForList(offerReq));
		}
		return result;
	}

	public void delete(Integer offerId) {
		reqOfferRepo.delete(offerId);
		
	}
	
	private OfferRequest mapperForList(TblUsrReqOffer fromTable){
		OfferRequest result = new OfferRequest();
		result.id = fromTable.getReqOffrId();
		result.description = fromTable.getReqOffrDesc();
		result.title = fromTable.getReqOffrTitle();
		result.intdAudience = fromTable.getTargAudienceId();
		result.createDate = fromTable.getCreateDt();
		result.updateDate = fromTable.getLastUpd();
		if(fromTable.getTblUserPersInfoSumry() != null){
			result.userId = fromTable.getTblUserPersInfoSumry().getUsrId();
			result.userName = fromTable.getTblUserPersInfoSumry().getFstName() +" "+fromTable.getTblUserPersInfoSumry().getLstName();
		}
		return result;
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
			question.response = quest.getResponse();
			questions.add(question);
		}
		result.questionList = questions;
		result.imgUrl = fromTable.getImageUrl();
		if(fromTable.getTblUserPersInfoSumry() != null){
			result.userId = fromTable.getTblUserPersInfoSumry().getUsrId();
			result.userName = fromTable.getTblUserPersInfoSumry().getFstName() +" "+fromTable.getTblUserPersInfoSumry().getLstName();
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
		
		result.userName = fromTable.getTblUserPersInfoSumry().getFstName() +" "+fromTable.getTblUserPersInfoSumry().getLstName();
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

	public List<OfferRequest> getOfferByUserId(Integer userId, OfferRequestEnum offer) {
		return getAll(userId, offer);
	}
}
