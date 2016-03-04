package com.businesskaro.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.salt.RandomSaltGenerator;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.web.util.UriUtils;


public class EncryptionUtil {
	private final static String ENCRYPTION_PASSWORD_SALT = "dsfds#$%FD$#%#$%##$%#$%$%^DFHGSEDF";	
	private static SecureRandom random = new SecureRandom();
	final static String ENCRYPTION_PASSWORD_RANDOM_SALT = nextSessionId(); 
	private static BasicTextEncryptor bte = new BasicTextEncryptor();
	
	
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
	
	//written by Mr.Nagendra on 03-March-2016
	public static String _encodeEncrypt(String text,String password){        
        try {
        	bte.setPassword(password);
            String encryptedText=bte.encrypt(text);
            System.out.println("Encrypted Text:"+encryptedText);
            String encodedText=URLEncoder.encode(encryptedText, "UTF-8");
            return encodedText;
	    } catch (UnsupportedEncodingException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	public static String _decodeDecrypt(String encryptedEncryptedCode){
		try 
		{
			String decodedText=URLDecoder.decode(encryptedEncryptedCode, "UTF-8");		
			return bte.decrypt(decodedText);
		} 
		catch (UnsupportedEncodingException e) 
		{
			 throw new RuntimeException(e);
		}
	}
}
