package com.businesskaro.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.entity.TblPolicy;
import com.businesskaro.entity.TblPolicySingleEntity;
import com.businesskaro.entity.TblUserManagement;
import com.businesskaro.entity.repo.TblPolicyRepo;
import com.businesskaro.entity.repo.TblPolicySingleEntityRepo;
import com.businesskaro.entity.repo.TblUserManagementRepo;
import com.businesskaro.model.BKException;
import com.businesskaro.model.Policy;
import com.businesskaro.security.SecureTokenUtil;
import com.businesskaro.service.PolicyService;

@RestController
public class PolicyRestService extends BKRestService {

	@Autowired
	PolicyService policyService;
	
	@Autowired
	TblUserManagementRepo managementRepo;
	@Autowired
	TblPolicyRepo policyRepo;
	
	@Autowired
	TblPolicySingleEntityRepo policySERepo;
	
	@Autowired
	SecureTokenUtil secureTokenUtil;
	
	@RequestMapping(value="/services/policy" , method = RequestMethod.POST)
	public Policy createPolicy(@RequestBody Policy policy,@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		try {
			Integer userId = validateSecureToken(secureTokenUtil, clientId,secureToken);
			validateSecureToken(secureTokenUtil,clientId, secureToken);
			policy.userId = userId;
			return policyService.createPolicy(policy);
		} catch (BKException e) {
			throw e;
		}
	}
	
	@RequestMapping(value="/services/policy" , method = RequestMethod.PUT)
	public Policy updatePolicy(@RequestBody Policy policy,@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		try {
			Integer userId = validateSecureToken(secureTokenUtil, clientId,secureToken);
			validateSecureToken(secureTokenUtil,clientId, secureToken);
			policy.userId = userId;
			return policyService.createPolicy(policy);
		} catch (Exception e) {
			throw e;
		}
	}
	//ADDED HERE -START
	//get all policies
	@RequestMapping(value = "/services/getAllPolicies", method = RequestMethod.GET)
	public List<TblPolicySingleEntity> getAllPolicies(
			@RequestHeader("SECURE_TOKEN") String secureToken,
			@RequestHeader("CLIENT_ID") String clientId)
	{
		List<TblPolicySingleEntity> allPolicies=null;
		try {
			Integer userId = validateSecureToken(secureTokenUtil, clientId, secureToken);
			TblUserManagement loggedInUser = managementRepo.findOne(userId);			
			
			 if(loggedInUser.getUsrType().equals("ADMIN")){ 	
				allPolicies = policySERepo.findAllByUsrId(userId);	 			
			} 
			else if(loggedInUser.getUsrType().equals("PUBLISHER")){ 	
					allPolicies = policySERepo.findAllByUsrId(userId);					
			}else {
				throw new BKException("User not an admin/publisher", "403",BKException.Type.IN_VALID_USER);
			}
		} catch (BKException ex) {
			throw ex;
		}		
		return allPolicies;
	}
	//get all isfeatured policies
	@RequestMapping(value = "/services/getAllIsFeaturedPolicies", method = RequestMethod.GET)
	public List<TblPolicySingleEntity> getAllIsFeaturedPolicies()
	{
		List<TblPolicySingleEntity> allPolicies=null;
		try 
		{			
			allPolicies = policySERepo.findAllByIsFeatured(1);
			return allPolicies;
		} 
		catch (BKException ex) {
			throw new BKException("User not an admin/publisher", "403",BKException.Type.IN_VALID_USER);
		}		
		
	}
	//ADDED HERE -END
	@RequestMapping(value="/services/policies" , method = RequestMethod.GET)
	public List<Policy> getPolicy(@PathVariable("policyId") Integer id,@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		try {
			validateSecureToken(secureTokenUtil,clientId, secureToken);
			return policyService.getPolicies();
		} catch (BKException e) {
			throw e;
		}
	}
	
	@RequestMapping(value="/services/policy/{policyId}" , method = RequestMethod.DELETE)
	public void deletePolicy(@PathVariable("policyId") Integer id,@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		try {
			validateSecureToken(secureTokenUtil,clientId, secureToken);
			policyService.deletePolicy(id);
		} catch (BKException e) {
			throw e;
		}
		 
	}
	
	@RequestMapping(value="/services/policy/{policyId}" , method = RequestMethod.GET)
	public Policy getPolicyById(@PathVariable("policyId") Integer id,@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		return policyService.getPolicy(id);
	}
	
	
	//Written by nagendra
	@RequestMapping(value="/services/policy/public/{policyId}" , method = RequestMethod.GET)
	public Policy getPublicPolicyById(@PathVariable("policyId") Integer id){
		return policyService.getPublicPolicy(id);		
	}
	
	@RequestMapping(value="/services/policysummary/{policyId}" , method = RequestMethod.GET)
	public Policy getPolicySummaryById(@PathVariable("policyId") Integer id){
		return policyService.getPolicy(id);
	}
	
}
