package com.businesskaro;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

//The below annotation makes our class as web controller and spring will consider it 
//when handling incoming web requests.


@SpringBootApplication
public class Application extends SpringBootServletInitializer{

	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

	
    public static void main(String[] args) {
    	 String webPort = System.getenv("PORT");
 	    if (webPort == null || webPort.isEmpty()) {
 	        webPort = "8080";
 	    }
 	    System.setProperty("server.port", webPort);
 	    //System.out.println("DB CONNECTION: "+DBConnection.getConnection());
 	    //SpringApplication.run(Application.class, args);	    
    	
    	
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
       
        
         String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        
       
    }

}