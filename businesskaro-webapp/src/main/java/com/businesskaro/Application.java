package com.businesskaro;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

import com.businesskaro.dao.DBConnection;

//The below annotation makes our class as web controller and spring will consider it 
//when handling incoming web requests.
@RestController
@EnableAutoConfiguration
public class Application {

	
	/*
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
    */
	public static void main(String[] args) throws Exception {  
	    String webPort = System.getenv("PORT");
	    if (webPort == null || webPort.isEmpty()) {
	        webPort = "9999"; 
	    }
	    System.setProperty("server.port", webPort);
	    System.out.println("DB CONNECTION: "+DBConnection.getConnection());
	    SpringApplication.run(Application.class, args);
	}

}