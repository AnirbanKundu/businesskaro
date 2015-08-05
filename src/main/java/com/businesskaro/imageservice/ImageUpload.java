package com.businesskaro.imageservice;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.Singleton;

import java.io.IOException;
import java.util.Map;

public class ImageUpload {

	@SuppressWarnings("rawtypes")
	public String uploadImage(byte[] data) throws IOException{
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name","difyxhuza","api_key","422449466261139","api_secret","aT9riLY1MyuzDpQqoyZD32Hfcvw"));
		Singleton.registerCloudinary(cloudinary);
		Map uploadResult = Singleton.getCloudinary().uploader().upload(data, ObjectUtils.asMap("resource_type", "auto"));
		
		return uploadResult.get("secure_url").toString();
	}
}
