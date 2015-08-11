package com.businesskaro.rest;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.businesskaro.imageservice.ImageUpload;
import com.businesskaro.rest.dto.ImageUploadResponse;

@RestController
public class ImageService {

	@Autowired
	ImageUpload imageUpload;
	
	
	@RequestMapping(value = "/services/upload", headers = "content-type=multipart/*", method = RequestMethod.POST)
	public ImageUploadResponse uploadImage(@RequestParam("file") MultipartFile file){
		try {
			return imageUpload.uploadImage(file.getBytes());
		} catch (IOException e) {
			System.out.println("In Exception : " + e);
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/services/delete/{fileId}/{resourceType}", method = RequestMethod.GET)
	public Map deleteImage(@PathVariable("fileId") String fileId, @PathVariable("resourceType") String resourceType){
		try {
			return imageUpload.deleteImage(fileId, resourceType);
		} catch (IOException e) {
			System.out.println("In Exception : " + e);
			e.printStackTrace();
		}
		
		return null;
	}
}
