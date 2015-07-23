package com.businesskaro.security;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.salt.RandomSaltGenerator;


public class EncryptionUtil {
	private final static String ENCRYPTION_PASSWORD_SALT = "dsfds#$%FD$#%#$%##$%#$%$%^DFHGSEDF";	
	private static SecureRandom random = new SecureRandom();
	final static String ENCRYPTION_PASSWORD_RANDOM_SALT = nextSessionId(); 
	
	
	public static String encode(String plainText, String randomSalt) {
		//System.out.println("Random Salt value is : " + randomSalt);
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
		config.setSaltGenerator(new RandomSaltGenerator());
		String fixedSalt = ENCRYPTION_PASSWORD_SALT;
		config.setPassword(fixedSalt + randomSalt);		
		config.setKeyObtentionIterations(4000);
		encryptor.setConfig(config);
		String encryptedPassword  = encryptor.encrypt(plainText);
		return encryptedPassword;			
	}
	
	public static String decode(String encodedText,String randomSalt) {
		//System.out.println("Random Salt value is : " + randomSalt);
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
		config.setSaltGenerator(new RandomSaltGenerator());
		String fixedSalt = ENCRYPTION_PASSWORD_SALT;
		config.setPassword(fixedSalt + randomSalt);		
		config.setKeyObtentionIterations(4000);
		encryptor.setConfig(config);
		return encryptor.decrypt(encodedText); 
	}
	
	
	public static String nextSessionId() {
		return new BigInteger(130, random).toString(32);
	}	
}
