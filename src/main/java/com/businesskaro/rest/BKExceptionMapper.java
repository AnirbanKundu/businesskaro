package com.businesskaro.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.businesskaro.model.BKException;

@Controller
@ControllerAdvice
public class BKExceptionMapper {

	@ExceptionHandler(BKException.class)
	@ResponseStatus(value=org.springframework.http.HttpStatus.BAD_REQUEST)
	public @ResponseBody String handleException(BKException bkException){
		System.out.println();
		return "{ \"message\" : \""+bkException.errorMsg+ "\" , \"code\" : \""+bkException.errorCode+ "\" , \"type\" : \""+bkException.errorType.name()+ "\"  }";
	}
}
