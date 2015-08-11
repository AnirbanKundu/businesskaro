package com.businesskaro.imageservice;

import com.businesskaro.rest.dto.ImageDetails;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.Singleton;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ImageUpload {

	@SuppressWarnings("rawtypes")
	public ImageDetails uploadImage(byte[] data) throws IOException{
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name","difyxhuza","api_key","422449466261139","api_secret","aT9riLY1MyuzDpQqoyZD32Hfcvw"));
		Singleton.registerCloudinary(cloudinary);
		Map uploadResult = Singleton.getCloudinary().uploader().upload(data, ObjectUtils.asMap("resource_type", "auto"));
		
		ImageDetails detail = new ImageDetails();
		detail.url = uploadResult.get("secure_url").toString(); 
		return  detail;
	}
}
