package com.businesskaro.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * NOTE: This class is not thread safe!
 * 
 * @author 204070171
 *
 */
public class EncryptionFactory {

	private static final Logger logger = Logger.getLogger(EncryptionFactory.class);
	 
	private static byte[] linebreak = {};
	private static final String passcode = "redAnother98#bug";
	SecretKeySpec _key;
	Cipher _cipher;
	Base64 _coder;
	boolean initialized = false;
	
	public EncryptionFactory()
	{
		try
		{
			_key = new SecretKeySpec(passcode.getBytes(), "AES");
			_cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
			_coder = new Base64(32,linebreak,true);
			initialized = true;
		}catch(Exception e){
			logger.error("Failed to initialize EncryptionFactory", e);
		}
	}
	
	/**
	 * Key and value should be in multiples of 8 to use this encryption
	 * @param passcode
	 */
	public EncryptionFactory(String passcode)
	{
		try
		{	
			_key = new SecretKeySpec(passcode.getBytes(), "DESede");
			_cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");;
			_coder = new Base64(32,linebreak,true);
			initialized = true;
		}
		catch(Exception e)
		{
			logger.error("Failed to initialize EncryptionFactory", e);
		}
	}
	
	public String Encrypt(String input) throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, Exception
	{
		if(input == null)
			return null;
		
		if(!initialized)
			throw new Exception("EncryptionFactory is not initialized");
		
		byte[] inBytes = input.getBytes("UTF-8");
	    _cipher.init(Cipher.ENCRYPT_MODE, _key);
	    
	    byte[] outBytes = _cipher.doFinal(inBytes);
	    
	    return new String(_coder.encode(outBytes));
	}
	
	public String Decrypt(String input) throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, Exception
	{
		if(input == null)
			return null;
		
		if(!initialized)
			throw new Exception("EncryptionFactory is not initialized");
		
		byte[] inBytes = _coder.decode(input.getBytes());
		_cipher.init(Cipher.DECRYPT_MODE, _key);
		
		byte[] outBytes = _cipher.doFinal(inBytes);
		return new String(outBytes);
	}

}
