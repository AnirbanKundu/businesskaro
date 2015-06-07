package com.businesskaro;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

//The below annotation makes our class as web controller and spring will consider it 
//when handling incoming web requests.
@RestController
@EnableAutoConfiguration
@RequestMapping("/home")
public class Application {

	//the below annotation provides routing information.
	@RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "Hello World!";
    }
	/*
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
    */
	public static void main(String[] args) throws Exception {  
	    String webPort = System.getenv("PORT");
	    if (webPort == null || webPort.isEmpty()) {
	        webPort = "8080";
	    }
	    System.setProperty("server.port", webPort);
	    SpringApplication.run(Application.class, args);
	}

}