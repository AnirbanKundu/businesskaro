package com.businesskaro.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.businesskaro.entity.BrgTopicsIndustry;
import com.businesskaro.entity.BrgTopicsState;
import com.businesskaro.entity.LkpIndustry;
import com.businesskaro.entity.LkpState;
import com.businesskaro.entity.TblPolicy;
import com.businesskaro.entity.repo.BrgTopicsIndustryRepo;
import com.businesskaro.entity.repo.BrgTopicsStateRepo;
import com.businesskaro.entity.repo.StateRepo;
import com.businesskaro.entity.repo.TblPolicyRepo;
import com.businesskaro.entity.repo.UserInductryRepo;
import com.businesskaro.model.BKException;
import com.businesskaro.model.BKException.Type;
import com.businesskaro.model.Policy;

@Service
public class PolicyService {

	@Autowired
	TblPolicyRepo policyRepo;
	
	@Autowired
	UserInductryRepo userIndRepo;
	
	@Autowired
	StateRepo stateRepo;
	
	@Autowired
	BrgTopicsIndustryRepo brgTopicsIndustryRepo;
	
	@Autowired
	BrgTopicsStateRepo brgTopicsStateRepo;
	
	public Policy createPolicy(Policy policy){
		
		TblPolicy tblPolicy = new TblPolicy();
		
		if(policy.id != null){
			tblPolicy.setPolicyId(policy.id);
		}
		
		tblPolicy.setCreateDt(new Date());
		tblPolicy.setLastUpd(new Date());
		tblPolicy.setPolicyDesc(policy.policyDesc);
		tblPolicy.setPolicyTitle(policy.policyTitle);
		tblPolicy.setPolicyType(policy.policyType);
		tblPolicy.setImageUrl(policy.imageUrl);
		tblPolicy.setIsFeatured(policy.isFeatured);
		tblPolicy.setUsrId(policy.userId);
		
		
		tblPolicy = policyRepo.save(tblPolicy);
		
		List<BrgTopicsIndustry> bgrList = brgTopicsIndustryRepo.findByTblPolicy(tblPolicy);
		if(bgrList != null && bgrList.size() > 0){
			brgTopicsIndustryRepo.delete(bgrList);
		}
	
		tblPolicy.setBrgTopicsIndustries(new ArrayList<BrgTopicsIndustry>());
		for (Integer indusId : policy.industrys) {
			LkpIndustry indEntity = userIndRepo.findOne(indusId);
			if (indEntity != null) {
				BrgTopicsIndustry industry = new BrgTopicsIndustry();
				industry.setLkpIndustry(indEntity);
				industry.setTblPolicy(tblPolicy);
				industry = brgTopicsIndustryRepo.save(industry);
				tblPolicy.addBrgTopicsIndustry(industry);
			}
		}
		
		List<BrgTopicsState> brgStateList = brgTopicsStateRepo.findByTblPolicy(tblPolicy);
		if(brgStateList != null && brgStateList.size() > 0){
			brgTopicsStateRepo.delete(brgStateList);
		}
		
		tblPolicy.setBrgTopicsStates(new ArrayList<BrgTopicsState>());
		for (Integer stateId : policy.states) {
			LkpState stateEntity = stateRepo.findOne(stateId);
			if (stateEntity != null) {
				BrgTopicsState stateBrg = new BrgTopicsState();
				stateBrg.setLkpState(stateEntity);
				stateBrg.setTblPolicy(tblPolicy);
				stateBrg = brgTopicsStateRepo.save(stateBrg);
				tblPolicy.getBrgTopicsStates().add(stateBrg);
			}
		}
		
		policyRepo.save(tblPolicy);
		return getPolicy(tblPolicy.getPolicyId());
	}
	
	public Policy getPolicy(Integer id){
		
		TblPolicy tblPolicy = policyRepo.findOne(id);
		
		if(tblPolicy == null){
			throw new BKException("Entity Not found with given id", "000", Type.ENTITY_NOT_FOUND);
		}
		return mapper(tblPolicy);
		
	}

	public void deletePolicy(Integer id) {
		
		TblPolicy tblPolicy = policyRepo.findOne(id);
		
		List<BrgTopicsIndustry> bgrList = brgTopicsIndustryRepo.findByTblPolicy(tblPolicy);
		if(bgrList != null && bgrList.size() > 0){
			brgTopicsIndustryRepo.delete(bgrList);
		}
		
		List<BrgTopicsState> brgStateList = brgTopicsStateRepo.findByTblPolicy(tblPolicy);
		if(brgStateList != null && brgStateList.size() > 0){
			brgTopicsStateRepo.delete(brgStateList);
		}
		
		policyRepo.delete(id);
		
	}

	public List<Policy> getPolicies() {
		Iterable<TblPolicy> tblPolicies = policyRepo.findAll();
		
		if(tblPolicies == null){
			throw new BKException("Entity Not found with given id", "000", Type.ENTITY_NOT_FOUND);
		}
		
		List<Policy> result = new ArrayList<Policy>();
		for(TblPolicy tblPolicy : tblPolicies){
			result.add(mapper(tblPolicy));
		}
		
		return result;
	}
	
	private Policy mapper(TblPolicy tblPolicy){
		Policy policy = new Policy();
		policy.imageUrl = tblPolicy.getImageUrl();
		policy.policyDesc = tblPolicy.getPolicyDesc();
		policy.policyTitle = tblPolicy.getPolicyTitle();
		policy.policyType = tblPolicy.getPolicyType();
		policy.id = tblPolicy.getPolicyId();
		policy.createDate = tblPolicy.getCreateDt();
		policy.updateDate = tblPolicy.getLastUpd();
		policy.isFeatured = tblPolicy.getIsFeatured();		
		policy.industrys = new int[tblPolicy.getBrgTopicsIndustries().size()];
		for(int j=0;j<tblPolicy.getBrgTopicsIndustries().size();j++){
			policy.industrys[j] = tblPolicy.getBrgTopicsIndustries().get(j).getLkpIndustry().getIndustryId();
		}
		
		policy.states = new int[tblPolicy.getBrgTopicsStates().size()];
		for(int i=0;i<tblPolicy.getBrgTopicsStates().size();i++){
			policy.states[i] = tblPolicy.getBrgTopicsStates().get(i).getLkpState().getStateId();
		}
		
		return policy;
	}
}
