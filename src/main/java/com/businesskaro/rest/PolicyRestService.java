package com.businesskaro.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.model.BKException;
import com.businesskaro.model.Policy;
import com.businesskaro.service.PolicyService;

@RestController
public class PolicyRestService extends BKRestService {

	@Autowired
	PolicyService policyService;
	
	@RequestMapping(value="/services/policy" , method = RequestMethod.POST)
	public Policy createPolicy(@RequestBody Policy policy,@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		try {
			validateSecureToken(clientId, secureToken);
			return policyService.createPolicy(policy);
		} catch (Exception e) {
			throw new BKException("Unauthorized User" , "000" , BKException.Type.IN_VALID_USER);
		}
	}
	
	@RequestMapping(value="/services/policy" , method = RequestMethod.PUT)
	public Policy updatePolicy(@RequestBody Policy policy,@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		try {
			validateSecureToken(clientId, secureToken);
			return policyService.createPolicy(policy);
		} catch (Exception e) {
			throw new BKException("Unauthorized User" , "000" , BKException.Type.IN_VALID_USER);
		}
	}
	
	@RequestMapping(value="/services/policies" , method = RequestMethod.GET)
	public List<Policy> getPolicy(@PathVariable("policyId") Integer id,@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		try {
			validateSecureToken(clientId, secureToken);
			return policyService.getPolicies();
		} catch (Exception e) {
			throw new BKException("Unauthorized User" , "000" , BKException.Type.IN_VALID_USER);
		}
	}
	
	@RequestMapping(value="/services/policy/{policyId}" , method = RequestMethod.DELETE)
	public void deletePolicy(@PathVariable("policyId") Integer id,@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		try {
			validateSecureToken(clientId, secureToken);
			policyService.deletePolicy(id);
		} catch (Exception e) {
			throw new BKException("Unauthorized User" , "000" , BKException.Type.IN_VALID_USER);
		}
		 
	}
	
	@RequestMapping(value="/services/policy/{policyId}" , method = RequestMethod.GET)
	public Policy getPolicyById(@PathVariable("policyId") Integer id,@RequestHeader("SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId){
		return policyService.getPolicy(id);
	}
	
}
