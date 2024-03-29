package com.businesskaro.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.entity.TblUserManagement;
import com.businesskaro.entity.repo.CustomRepository;
import com.businesskaro.entity.repo.TblUserManagementRepo;
import com.businesskaro.model.BKEntityType;
import com.businesskaro.model.AdminEntitySearch;
import com.businesskaro.model.BKException;
import com.businesskaro.security.SecureTokenUtil;

@RestController
public class UserManagementRestService extends BKRestService {

	@Autowired
	private TblUserManagementRepo managementRepo;

	@Autowired
	SecureTokenUtil secureTokenUtil;
	
	@Autowired
	CustomRepository customRepo;

	@RequestMapping(value = "/admin/manageUsers", method = RequestMethod.GET)
	public List<TblUserManagement> getAllUsers(
			@RequestHeader("SECURE_TOKEN") String secureToken,
			@RequestHeader("CLIENT_ID") String clientId) {
		// Integer userId = null;
		try {
			Integer userId = validateSecureToken(secureTokenUtil, clientId,
					secureToken);
			TblUserManagement loggedInUser = managementRepo.findOne(userId);
			if(loggedInUser.getUsrType().equals("ADMIN")){ // Uncomment
			//if (loggedInUser.getUsrType().equals("USER")) { // Comment
				List<TblUserManagement> userMgmt = managementRepo.findAll();
				List<TblUserManagement> toRemove = new ArrayList<TblUserManagement>();
				for(TblUserManagement a: userMgmt){
				    if(a.getUsrId() == userId){
				    	toRemove.add(a);
				    }
				}
				 userMgmt.removeAll(toRemove);;	
				 return userMgmt;
				
			} else {
				throw new BKException("User not an admin", "403",
						BKException.Type.USER_AUTHENTICATION_FAIL);
			}
		} catch (BKException ex) {
			throw new BKException("User not an admin", "403",
					BKException.Type.USER_AUTHENTICATION_FAIL);
		}

	}
	/*
	 * "SECURE_TOKEN") String secureToken, 
			@RequestHeader("CLIENT_ID") String clientId, @RequestBody BkUserProfile bkUserProfile
	 */

	@RequestMapping(value = "/admin/manageUsers", method = RequestMethod.POST)
	public TblUserManagement updateUser(
			@RequestBody TblUserManagement updateUser,
			@RequestHeader("SECURE_TOKEN") String secureToken,
			@RequestHeader("CLIENT_ID") String clientId) {
		try {
			Integer userId = validateSecureToken(secureTokenUtil, clientId,
					secureToken);
			TblUserManagement loggedInUser = managementRepo.findOne(userId);
			 if(loggedInUser.getUsrType().equals("ADMIN")){ // Uncomment
			//if (loggedInUser.getUsrType().equals("USER")) { // Comment
				updateUser.setLastUpd(new Date());
				return managementRepo.save(updateUser);
				//return managementRepo.saveAndFlush(updateUser);
			} else {
				throw new BKException("User not an admin", "403",
						BKException.Type.USER_AUTHENTICATION_FAIL);
			}
			
		} catch (BKException ex) {
			throw new BKException(ex.errorMsg, ex.errorCode,
					ex.errorType);
		}
	}
	
	@RequestMapping(value = "/admin/manageEntity", method = RequestMethod.GET)
	public List<AdminEntitySearch> getEntitiesByParam(@RequestHeader("SECURE_TOKEN") String secureToken,
			@RequestHeader("CLIENT_ID") String clientId,
			@RequestParam("entityType") String entityType,@RequestParam("industryId") Integer industryId,@RequestParam("stateId") Integer stateId){
		List<AdminEntitySearch> adminsearch = null;
		try{
			Integer userId = validateSecureToken(secureTokenUtil, clientId,
					secureToken);
			TblUserManagement loggedInUser = managementRepo.findOne(userId);
			 if(loggedInUser.getUsrType().equals("ADMIN")){
				 try {
						adminsearch =  customRepo.searchEnityForAdmin(entityType,industryId,stateId); 
					} catch (Exception e) {
						// TODO Auto-generated catch block
						throw new BKException("database error", "500",
								BKException.Type.EXTERNAL_ERRROR);
					}
					System.out.println("THe count of admin search is : "+ adminsearch.size());
			 }else {
					throw new BKException("User not an admin", "403",
							BKException.Type.USER_AUTHENTICATION_FAIL);
			}
			
			
		}catch(BKException ex){
			throw ex;
		}
		return adminsearch;
	}
}