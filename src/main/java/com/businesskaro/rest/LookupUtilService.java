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
import com.businesskaro.entity.AgeGroup;
import com.businesskaro.entity.Education;
import com.businesskaro.entity.LkpIndustry;
import com.businesskaro.entity.repo.AgeGroupRepo;
import com.businesskaro.entity.repo.EducationRepo;
import com.businesskaro.entity.repo.ExperienceRepo;
import com.businesskaro.entity.repo.ProfessionRepo;
import com.businesskaro.entity.repo.UserInductryRepo;
import com.businesskaro.model.Industry1;

@RestController
class LookupUtilService { 	
	Logger logger = Logger.getLogger(LookupUtilService.class.getName());	
	
	
	@Autowired
	AgeGroupRepo ageGroupRepo;
	
	@Autowired
	EducationRepo educationRepo;
	
	@Autowired
	ProfessionRepo professionRepo;
	
	@Autowired
	ExperienceRepo experienceRepo;
	
	@Autowired
	UserInductryRepo industryRepo;
	
	
    @RequestMapping(value="/utilservices/ages", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public List<AgeGroup> getAllAges() throws Exception {   
    	//List<AgeGroup> ages = new ArrayList<AgeGroup>();
    	 return ageGroupRepo.findAll();
    	//return lkputildao.getAgeGroups();
    }
    @RequestMapping(value="/utilservices/education", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public Iterable<Education> getAllEducation() throws Exception {   
    	//List<AgeGroup> ages = new ArrayList<AgeGroup>();
    	return educationRepo.findAll();
    }
    @RequestMapping(value="/utilservices/industries", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public Iterable<LkpIndustry> getAllIndustry() throws Exception {   
    	//List<AgeGroup> ages = new ArrayList<AgeGroup>();
    	 return industryRepo.findAll();
    }
}