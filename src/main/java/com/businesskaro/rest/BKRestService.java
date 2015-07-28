package com.businesskaro.rest;

import com.businesskaro.model.BKException;
import com.businesskaro.security.SecureTokenUtil;

public class BKRestService {

	public Integer validateSecureToken(String clientId, String secureToken){
		try {
			return SecureTokenUtil.validateSecureToken(clientId, secureToken);
		} catch (Exception e) {
			throw new BKException("Unknown Error while generating secure token" , "000" , BKException.Type.INTERNAL_ERRROR);
		}
	}
}
