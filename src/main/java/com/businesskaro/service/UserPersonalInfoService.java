package com.businesskaro.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.businesskaro.entity.AgeGroup;
import com.businesskaro.entity.BrgUsrIndustry;
import com.businesskaro.entity.BrgUsrLookingFor;
import com.businesskaro.entity.Education;
import com.businesskaro.entity.LkpExperience;
import com.businesskaro.entity.LkpIndustry;
import com.businesskaro.entity.LkpProfession;
import com.businesskaro.entity.LkpSkill;
import com.businesskaro.entity.LkpState;
import com.businesskaro.entity.TblUserPasswordProfile;
import com.businesskaro.entity.UserPersonalInfo;
import com.businesskaro.entity.UserPersonalInfoDetails;
import com.businesskaro.entity.UserPersonalInfoSummary;
import com.businesskaro.entity.UserSkill;
import com.businesskaro.entity.repo.AgeGroupRepo;
import com.businesskaro.entity.repo.BrgUsrIndustryRepo;
import com.businesskaro.entity.repo.BrgUsrLookingForRepo;
import com.businesskaro.entity.repo.EducationRepo;
import com.businesskaro.entity.repo.ExperienceRepo;
import com.businesskaro.entity.repo.ProfessionRepo;
import com.businesskaro.entity.repo.SkillRepo;
import com.businesskaro.entity.repo.StateRepo;
import com.businesskaro.entity.repo.TblUserPasswordProfileRepo;
import com.businesskaro.entity.repo.UserInductryRepo;
import com.businesskaro.entity.repo.UserPersonalInfoSummaryRepo;
import com.businesskaro.entity.repo.UserProfileInfoDetailsRepo;
import com.businesskaro.entity.repo.UserSkillRepo;
import com.businesskaro.model.BKException;
import com.businesskaro.model.BKUserProfileDetails;
import com.businesskaro.model.BKUserProfileSummary;
import com.businesskaro.model.BkUserProfile;

@Component
public class UserPersonalInfoService {

	@Autowired
	UserPersonalInfoSummaryRepo summaryRepo;

	@Autowired
	UserProfileInfoDetailsRepo detailsRepo;
	
	@Autowired
	TblUserPasswordProfileRepo tblUserPasswordProfileRepo;
	

	@Autowired
	AgeGroupRepo ageGroupRepo;

	@Autowired
	EducationRepo EducationRepo;

	@Autowired
	StateRepo stateRepo;

	@Autowired
	ExperienceRepo expRepo;

	@Autowired
	ProfessionRepo professionRepo;

	@Autowired
	SkillRepo skillRepo;

	@Autowired
	UserSkillRepo userSkillRepo;

	@Autowired
	UserInductryRepo userIndRepo;

	@Autowired
	BrgUsrIndustryRepo brgUsrIndustryRepo;

	@Autowired
	BrgUsrLookingForRepo brgUsrLookingForRepo;

	@Transactional
	public BkUserProfile createOrUpdateUserPersonalInfo(
			BkUserProfile bkUserProfile, boolean isCreate) {

		UserPersonalInfoDetails personalInfoEntiyDetails = createDetails(
				bkUserProfile.details, isCreate);
		UserPersonalInfoSummary personalInfoEntiySummary = createSummary(
				bkUserProfile.summary, isCreate);
		if(isCreate==true){
			//EntityManagerFactory factory = Persistence.createEntityManagerFactory("TblUserPassword");
			//EntityManager em = factory.createEntityManager();
			
		}

		return getUserPersonalInfo(personalInfoEntiyDetails.getUsrId());
	}

	private UserPersonalInfoSummary createSummary(BKUserProfileSummary summary,
			boolean isCreate) {

		UserPersonalInfoSummary summaryEntiy = new UserPersonalInfoSummary();
		summaryEntiy.setAboutMe(summary.aboutMe);
		summaryEntiy.setCityName(summary.cityName);
		summaryEntiy.setCompanyUrl(summary.companyUrl);
		if (isCreate) {
			summaryEntiy.setCreateDt(new Date());
		}
		summaryEntiy.setFstName(summary.firstName);
		summaryEntiy.setLastUpd(new Date());
		summaryEntiy.setUsrId(summary.userId);
		summaryEntiy.setUsrTyp(summary.userType);
		summaryEntiy.setLstName(summary.lastName);
		summaryEntiy.setStateName(summary.stateName);
		summaryEntiy = summaryRepo.save(summaryEntiy);
		
		if(isCreate==true){
			System.out.println("GetUsrId : "+ summaryEntiy.getUsrId());
			TblUserPasswordProfile arg = new TblUserPasswordProfile();
			arg.setUsrId(summaryEntiy.getUsrId());
			arg.setProfileCreated(1);
			tblUserPasswordProfileRepo.save(arg);
			System.out.println("SUCCESS in  UPDATING TBL PWD ");
		}		
		
		if (summary.userSkills != null)
			for (Integer skillId : summary.userSkills) {
				LkpSkill skillEntity = skillRepo.findOne(skillId);
				System.out.println(">>>>>>>>>>>> " + skillEntity);
				if (skillEntity != null) {
					UserSkill userSkill = new UserSkill();
					userSkill.setLkpSkill(skillEntity);
					userSkill.setTblUserPersInfoSumry(summaryEntiy);
					userSkillRepo.save(userSkill);
				}
			}

		if (summary.industrys != null)
			for (Integer indusId : summary.industrys) {
				LkpIndustry indEntity = userIndRepo.findOne(indusId);
				if (indEntity != null) {
					BrgUsrIndustry usrIndustry = new BrgUsrIndustry();
					usrIndustry.setLkpIndustry(indEntity);
					usrIndustry.setTblUserPersInfoSumry(summaryEntiy);
					brgUsrIndustryRepo.save(usrIndustry);
				}
			}

		if (summary.lookinfForSkill != null)
			for (Integer skillLokkId : summary.lookinfForSkill) {
				LkpSkill skillEntity = skillRepo.findOne(skillLokkId);
				if (skillEntity != null) {
					BrgUsrLookingFor lookingFor = new BrgUsrLookingFor();
					lookingFor.setLkpSkill(skillEntity);
					lookingFor.setTblUserPersInfoSumry(summaryEntiy);
					brgUsrLookingForRepo.save(lookingFor);
				}
			}

		return summaryEntiy;
	}

	private UserPersonalInfoDetails createDetails(
			BKUserProfileDetails bkUserProfileDetails, boolean isCreate) {

		AgeGroup ageGroup = ageGroupRepo
				.findOne(bkUserProfileDetails.ageGroupId);

		Education education = EducationRepo
				.findOne(bkUserProfileDetails.educatonId);

		LkpState state = stateRepo.findOne(bkUserProfileDetails.stateId);

		LkpExperience experience = expRepo
				.findOne(bkUserProfileDetails.experienceId);

		LkpProfession profession = professionRepo
				.findOne(bkUserProfileDetails.professionalId);

		UserPersonalInfo personalInfoEntiy = new UserPersonalInfo();
		personalInfoEntiy.details = new UserPersonalInfoDetails();

		personalInfoEntiy.details.setUsrId(bkUserProfileDetails.userId);
		personalInfoEntiy.details.setLkpAgeGrp(ageGroup);
		personalInfoEntiy.details.setLkpEducation(education);
		personalInfoEntiy.details.setLkpState(state);
		personalInfoEntiy.details.setLkpExperience(experience);
		personalInfoEntiy.details.setLkpProfession(profession);

		if (isCreate) {
			personalInfoEntiy.details.setCreateDt(new Date());
		}
		personalInfoEntiy.details.setLastUpd(new Date());

		personalInfoEntiy.details.setFbUrl(bkUserProfileDetails.faceBookUrl);
		personalInfoEntiy.details.setImageUrl(bkUserProfileDetails.imageUrl);
		personalInfoEntiy.details
				.setLinkdInUrl(bkUserProfileDetails.linkedInUrl);
		personalInfoEntiy.details.setTwitrUrl(bkUserProfileDetails.twiterURL);
		detailsRepo.save(personalInfoEntiy.details);

		return personalInfoEntiy.details;
	}

	public BkUserProfile getUserPersonalInfo(Integer id) {

		BkUserProfile info = new BkUserProfile();
		info.details = new BKUserProfileDetails();
		info.summary = new BKUserProfileSummary();

		UserPersonalInfoSummary summaryEntity = summaryRepo.findOne(id);
		UserPersonalInfoDetails detailEntity = detailsRepo.findOne(id);
		
		if(summaryEntity == null &&  detailEntity == null){
			throw new BKException("USER PROFILE NOT FOUND", "000", BKException.Type.ENTITY_NOT_FOUND);
		}

		if(detailEntity!= null){
			info.details = detailsMapper(detailEntity);
		}
		
		if(summaryEntity != null){
			info.summary = summryMapper(summaryEntity);
		}
		return info;
	}
	
	public BKUserProfileSummary getUserPersonalSummary(Integer id){
		UserPersonalInfoSummary summaryEntity = summaryRepo.findOne(id);
		return summryMapper(summaryEntity);
	} 
	
	
	public BKUserProfileDetails getUserPersonalDetails(Integer id){
		UserPersonalInfoDetails detailEntity = detailsRepo.findOne(id);
		return detailsMapper(detailEntity);
	} 
	
	private BKUserProfileSummary summryMapper(
			UserPersonalInfoSummary summryEntity) {
		BKUserProfileSummary summary = new BKUserProfileSummary();

		summary.aboutMe = summryEntity.getAboutMe();
		summary.cityName = summryEntity.getCityName();
		summary.companyUrl = summryEntity.getCompanyUrl();
		summary.userId = summryEntity.getUsrId();
		summary.createdDate = summryEntity.getCreateDt();
		summary.firstName = summryEntity.getFstName();
		summary.companyUrl = summryEntity.getCompanyUrl();
		summary.lastName = summryEntity.getLstName();
		summary.stateName = summryEntity.getStateName();
		summary.updatedDate = summryEntity.getLastUpd();
		summary.userType = summryEntity.getUsrTyp();
		summary.imageUrl = summryEntity.getImageUrl();

		List<UserSkill> skillList = summryEntity.getBrgUsrSkills();
		if (skillList != null) {
			int[] skillIds = new int[skillList.size()];
			int i = 0;
			for (UserSkill userSkill : skillList) {
				skillIds[i++] = userSkill.getLkpSkill().getSkillId();
			}
			
			summary.userSkills = toSet(skillIds);
		}

		List<BrgUsrIndustry> usrIndList = summryEntity.getBrgUsrIndustries();
		if (usrIndList != null) {
			int[] indId = new int[usrIndList.size()];
			int i = 0;
			for (BrgUsrIndustry usrInd : usrIndList) {
				indId[i++] = usrInd.getLkpIndustry().getIndustryId();
			}
			summary.industrys = toSet(indId);
		}

		List<BrgUsrLookingFor> usrLookingForList = summryEntity
				.getBrgUsrLookingFors();
		if (usrLookingForList != null) {
			int[] skillIds = new int[skillList.size()];
			int i = 0;
			for (BrgUsrLookingFor userSkill : usrLookingForList) {
				skillIds[i++] = userSkill.getLkpSkill().getSkillId();
			}
			summary.lookinfForSkill = toSet(skillIds);
		}

		return summary;
	}

	private BKUserProfileDetails detailsMapper(
			UserPersonalInfoDetails detailsEntity) {

		BKUserProfileDetails details = new BKUserProfileDetails();

		if(detailsEntity.getLkpAgeGrp() != null){
			details.ageGroupId = detailsEntity.getLkpAgeGrp().getAgeId();
		}
		
		details.createdDate = detailsEntity.getCreateDt();
		details.updatedDate = detailsEntity.getLastUpd();

		if(detailsEntity.getLkpEducation() != null){
			details.educatonId = detailsEntity.getLkpEducation().getEductnId();
		}
		
		if(detailsEntity.getLkpExperience() != null)
		details.experienceId = detailsEntity.getLkpExperience()
				.getExperienceId();
		
		if(detailsEntity.getLkpProfession() != null)
		details.professionalId = detailsEntity.getLkpProfession()
				.getProfsionId();
		
		if(detailsEntity.getLkpState() != null)
		details.stateId = detailsEntity.getLkpState().getStateId();
		
		if(detailsEntity.getLkpAgeGrp() != null)
		details.ageGroupId = detailsEntity.getLkpAgeGrp().getAgeId();

		details.faceBookUrl = detailsEntity.getFbUrl();
		details.twiterURL = detailsEntity.getTwitrUrl();
		details.imageUrl = detailsEntity.getImageUrl();
		details.linkedInUrl = detailsEntity.getLinkdInUrl();

		return details;

	}
	
	
	int[] toArray(Set<Integer> set){
		int[] arr = new int[set.size()];
		int i =0;
		for(Integer e : set){
			arr[i++] =e;
		}
		return arr;
	}
	
	public Set<Integer> toSet(int[] arr){
		Set<Integer> set = new HashSet<Integer>();
		for(int i : arr){
			set.add(i);
		}
		return set;
	}

	public BkUserProfile getUserPersonalInfoSummary(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
