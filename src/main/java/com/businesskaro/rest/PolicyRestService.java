package com.businesskaro.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.model.Policy;
import com.businesskaro.service.PolicyService;

@RestController
public class PolicyRestService {

	@Autowired
	PolicyService policyService;
	
	@RequestMapping(value="/services/policy" , method = RequestMethod.POST)
	public Policy createPolicy(@RequestBody Policy policy){
		return policyService.createPolicy(policy);
	}
	
	@RequestMapping(value="/services/policy" , method = RequestMethod.PUT)
	public Policy updatePolicy(@RequestBody Policy policy){
		return policyService.createPolicy(policy);
	}
	
	@RequestMapping(value="/services/policy/{policyId}" , method = RequestMethod.GET)
	public Policy getPolicy(@PathVariable("policyId") Integer id){
		return policyService.getPolicy(id);
	}
	
	@RequestMapping(value="/services/policy/{policyId}" , method = RequestMethod.DELETE)
	public void deletePolicy(@PathVariable("policyId") Integer id){
		 policyService.deletePolicy(id);
	}
	
}
