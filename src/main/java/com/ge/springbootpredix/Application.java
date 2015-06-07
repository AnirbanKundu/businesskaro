package com.ge.springbootpredix;
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

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}