package com.businesskaro.imageservice;

import com.businesskaro.rest.dto.ImageUploadResponse;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.Singleton;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ImageUpload {
	
	private Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name","difyxhuza","api_key","422449466261139","api_secret","aT9riLY1MyuzDpQqoyZD32Hfcvw"));
	
	
	@SuppressWarnings("rawtypes")
	public ImageUploadResponse uploadImage(byte[] data) throws IOException{
		Singleton.registerCloudinary(cloudinary);
		Map uploadResult = Singleton.getCloudinary().uploader().upload(data, ObjectUtils.asMap("resource_type", "auto"));
		
		ImageUploadResponse detail = new ImageUploadResponse();
		//get the secure URL
		detail.url = uploadResult.get("secure_url").toString();
		// get the public id, can be used to delete the file uploaded
		detail.publicId = uploadResult.get("public_id").toString();
		return  detail;
	}
	
	
	/**
	 * 
	 * @param publicId: ID of the file
	 * @param resourceType: Type of file 'image', 'css', 'javascript' etc 
	 * @return Map with JSPN structure {result:'the result'}
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public Map deleteImage(String publicId, String resourceType) throws IOException{
		Singleton.registerCloudinary(cloudinary);
		Map destroyResult = Singleton.getCloudinary().uploader().destroy(publicId, ObjectUtils.asMap("resource_type", resourceType));
		System.out.println(destroyResult);
		
		return destroyResult;
	}
}
