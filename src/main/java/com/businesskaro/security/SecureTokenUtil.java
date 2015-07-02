package com.businesskaro.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import org.apache.log4j.Logger;

public class SecureTokenUtil {

	private static final String TOKEN_SEP = "_._";
	
	private static final SimpleDateFormat smf = new SimpleDateFormat("MMddyyyyHHmmss");
	
	private static final Logger logger = Logger.getLogger(SecureTokenUtil.class);
	
	public static boolean validateSecureToken(String clientId, String secureToken)
			throws Exception {
		boolean isValid =true;
		try{
			if(secureToken != null && clientId != null){
				//Decrypt Secure token
				EncryptionFactory encFactory = new EncryptionFactory(clientId);
				String decString = encFactory.Decrypt(secureToken);
				//Split secure token
				StringTokenizer tokenizer = new StringTokenizer(decString, TOKEN_SEP); 
				String guid = tokenizer.nextToken();
				//Valid GUID
				boolean isGuidValidate = BKGuid.isValidGUID(guid);
				logger.debug(" GUID Extracted : " + guid);
				if(!isGuidValidate){
					logger.info("Secure token failed. Its not a valid guid");
					isValid = false;
				} else{
					//Validate Date format
					String endDateStr = tokenizer.nextToken();
					logger.debug(" End Date Extracted : " + endDateStr);
					Date endDate = parseTotokenDateFormat(endDateStr);
					Date currentDate = new Date();
					//Verify if the token is expired
					if(endDate.compareTo(currentDate) < 1){
						logger.info("Secure token failed. Token expired");
						isValid = false;
					}
				}
			}else{
				isValid = false;
			}
		}catch(Exception e){
			logger.error("Error while processing secure token",e);
			isValid = false;
		}
		
		if(! isValid){
			logger.error("Secure token is not valid.");
			throw new Exception("Securetoken is not valid / Expired");
		}
		
		return isValid;
	}

	public static String generateSecurityToken(String newGuid)
			throws InvalidKeyException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException, Exception {
		EncryptionFactory encFactory = new EncryptionFactory(newGuid);
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.HOUR_OF_DAY, 1);
		String tokenEndLife = fomratTotokenDateFormat(cal.getTime());
		return encFactory.Encrypt(newGuid + TOKEN_SEP + tokenEndLife );
	}

	private static String fomratTotokenDateFormat(Date date) {
		
		return smf.format(date);
	}
	
	private static Date parseTotokenDateFormat(String dateStr) throws ParseException{
		return smf.parse(dateStr);
	}

}
