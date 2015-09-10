package com.businesskaro.rest;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.entity.AgeGroup;
import com.businesskaro.entity.Education;
import com.businesskaro.entity.LkpIndustry;
import com.businesskaro.entity.LkpQuestion;
import com.businesskaro.entity.LkpState;
import com.businesskaro.entity.LkpTargAudience;
import com.businesskaro.entity.repo.AgeGroupRepo;
import com.businesskaro.entity.repo.EducationRepo;
import com.businesskaro.entity.repo.ExperienceRepo;
import com.businesskaro.entity.repo.IntendedAudienceRepo;
import com.businesskaro.entity.repo.ProfessionRepo;
import com.businesskaro.entity.repo.QuestionRepo;
import com.businesskaro.entity.repo.StateRepo;
import com.businesskaro.entity.repo.UserInductryRepo;

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
	
	@Autowired
	QuestionRepo questionsRepo;
	
	@Autowired
	StateRepo stateRepo;
	
	@Autowired
	IntendedAudienceRepo audienceRepo;
	
    @RequestMapping(value="/utilservices/ages", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public List<AgeGroup> getAllAges() throws Exception {   
    	 return ageGroupRepo.findAll();
    }
    @RequestMapping(value="/utilservices/education", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public Iterable<Education> getAllEducation() throws Exception {   
    	return educationRepo.findAll();
    }
    @RequestMapping(value="/utilservices/industries", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public Iterable<LkpIndustry> getAllIndustry() throws Exception {   
    	 return industryRepo.findAll();
    }
    @RequestMapping(value="/utilservices/questions/{questionType}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public Iterable<LkpQuestion> getAllQuestions(@PathVariable("questionType") String questionType) throws Exception {   
    	return questionsRepo.findByQuestTyp(questionType);
    }
    @RequestMapping(value="/utilservices/states", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public Iterable<LkpState> getAllStates() throws Exception {   
    	 return stateRepo.findAll();
    }
    @RequestMapping(value="/utilservices/intendedAudience", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public Iterable<LkpTargAudience> getIntendedAudience() throws Exception {   
    	 return audienceRepo.findAll();
    }
}