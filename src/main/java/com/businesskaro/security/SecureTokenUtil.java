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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.businesskaro.model.BKException;
import com.businesskaro.model.BKException.Type;

@Component
public class SecureTokenUtil {

	private static final String TOKEN_SEP = "_._";

	private static final SimpleDateFormat smf = new SimpleDateFormat(
			"MMddyyyyHHmmss");

	private static final Logger logger = Logger
			.getLogger(SecureTokenUtil.class);

	@Autowired
	SecureTokenCache tokenCache;

	public Integer validateSecureToken(String clientId, String secureToken)
			throws Exception {
		boolean isValid = true;
		try {
			if (secureToken != null && clientId != null) {
				// Decrypt Secure token
				EncryptionFactory encFactory = new EncryptionFactory(clientId);
				String decString = encFactory.Decrypt(secureToken);
				// Split secure token
				StringTokenizer tokenizer = new StringTokenizer(decString,
						TOKEN_SEP);
				String guid = tokenizer.nextToken();
				// Valid GUID
				boolean isGuidValidate = BKGuid.isValidGUID(guid);
				logger.debug(" GUID Extracted : " + guid);
				if (!isGuidValidate) {
					logger.info("Secure token failed. Its not a valid guid");
					isValid = false;
				} else {
					// Validate Date format
					String endDateStr = tokenizer.nextToken();
					logger.debug(" End Date Extracted : " + endDateStr);
					Date endDate = parseTotokenDateFormat(endDateStr);
					Date currentDate = new Date();
					// Verify if the token is expired
					if (endDate.compareTo(currentDate) < 1) {
						logger.info("Secure token failed. Token expired");						
						isValid = false;
						throw new BKException("Securetoken is not valid / Expired", "000",
								Type.USER_AUTH_FAIL);
					}
					String userIdStr = tokenizer.nextToken();
					logger.debug("User Id in Token :" + userIdStr);
					if (userIdStr == null) {
						throw new BKException(
								"Securetoken is not valid / Expired", "000",
								Type.USER_AUTH_FAIL);
					}

					isValid = tokenCache.isTokenValid(secureToken);

					if (isValid) {
						return Integer.parseInt(userIdStr);
					}

				}
			} else {
				isValid = false;
			}
		} catch (Exception e) {
			logger.error("Error while processing secure token", e);
			isValid = false;
		}

		if (!isValid) {
			logger.error("Secure token is not valid.");
			throw new BKException("Securetoken is not valid / Expired", "000",
					Type.USER_AUTH_FAIL);
		}

		return null;
	}

	public String validateEmailToken(String clientId, String secureToken)
			throws Exception {
		boolean isValid = true;
		try {
			if (secureToken != null && clientId != null) {
				// Decrypt Secure token
				EncryptionFactory encFactory = new EncryptionFactory(clientId);
				String decString = encFactory.Decrypt(secureToken);
				// Split secure token
				StringTokenizer tokenizer = new StringTokenizer(decString,
						TOKEN_SEP);
				String guid = tokenizer.nextToken();
				// Valid GUID
				boolean isGuidValidate = BKGuid.isValidGUID(guid);
				logger.debug(" GUID Extracted : " + guid);
				if (!isGuidValidate) {
					logger.info("Secure token failed. Its not a valid guid");
					isValid = false;
				} else {
					// Validate Date format
					String endDateStr = tokenizer.nextToken();
					logger.debug(" End Date Extracted : " + endDateStr);
					Date endDate = parseTotokenDateFormat(endDateStr);
					Date currentDate = new Date();
					// Verify if the token is expired
					if (endDate.compareTo(currentDate) < 1) {
						logger.info("Secure token failed. Token expired");
						isValid = false;
					}
					String userIdStr = tokenizer.nextToken();
					logger.debug("User Id in Token :" + userIdStr);
					if (userIdStr == null) {
						throw new BKException(
								"Securetoken is not valid / Expired", "000",
								Type.USER_AUTH_FAIL);
					}
					return userIdStr;
				}
			} else {
				isValid = false;
			}
		} catch (Exception e) {
			logger.error("Error while processing secure token", e);
			isValid = false;
		}

		if (!isValid) {
			logger.error("Secure token is not valid.");
			throw new BKException("Securetoken is not valid / Expired", "000",
					Type.USER_AUTH_FAIL);
		}

		return null;
	}

	public String generateSecurityToken(String newGuid, Integer userId)
			throws InvalidKeyException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException, Exception {
		EncryptionFactory encFactory = new EncryptionFactory(newGuid);
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.HOUR_OF_DAY, 1);
		String tokenEndLife = fomratTotokenDateFormat(cal.getTime());
		String secureToken = encFactory.Encrypt(newGuid + TOKEN_SEP
				+ tokenEndLife + TOKEN_SEP + userId.toString());
		tokenCache.addToken(secureToken);
		return secureToken;
	}

	public String generateEmailToken(String newGuid, String rawToken)
			throws InvalidKeyException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException, Exception {
		EncryptionFactory encFactory = new EncryptionFactory(newGuid);
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.HOUR_OF_DAY, 1);
		String tokenEndLife = fomratTotokenDateFormat(cal.getTime());
		String secureToken = encFactory.Encrypt(newGuid + TOKEN_SEP
				+ tokenEndLife + TOKEN_SEP + rawToken);
		return secureToken;
	}

	public void inValidateSecureToken(String secureToken) {
		tokenCache.removeToken(secureToken);
	}

	private static String fomratTotokenDateFormat(Date date) {

		return smf.format(date);
	}

	private static Date parseTotokenDateFormat(String dateStr)
			throws ParseException {
		return smf.parse(dateStr);
	}

}
