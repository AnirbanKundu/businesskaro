package com.businesskaro.rest;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.dao.LookUpUtilDao;
import com.businesskaro.model.AgeGroup1;
import com.businesskaro.model.Education1;
import com.businesskaro.model.Industry1;

@RestController
class LookupUtilService { 	
	Logger logger = Logger.getLogger(LookupUtilService.class.getName());	
	@Autowired
	LookUpUtilDao lkputildao;
	
    @RequestMapping(value="/utilservices/ages", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public List<AgeGroup1> getAllAges() throws Exception {   
    	//List<AgeGroup> ages = new ArrayList<AgeGroup>();
    	return lkputildao.getAgeGroups();
    }
    @RequestMapping(value="/utilservices/education", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public List<Education1> getAllEducation() throws Exception {   
    	//List<AgeGroup> ages = new ArrayList<AgeGroup>();
    	return lkputildao.getAllEducation();
    }
    @RequestMapping(value="/utilservices/industries", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public List<Industry1> getAllIndustry() throws Exception {   
    	//List<AgeGroup> ages = new ArrayList<AgeGroup>();
    	return lkputildao.getAllIndustry();
    }
}