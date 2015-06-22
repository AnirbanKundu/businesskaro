package com.businesskaro.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class DBConnection {
	
	public JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	public void getJdbCTemplate() throws URISyntaxException, ClassNotFoundException{
		 System.out.println("ENV BUSINESSKARO: "+System.getenv("CLEARDB_DATABASE_URL"));
         String str = System.getenv("CLEARDB_DATABASE_URL");
         if(str == null){
                 str = "mysql://b057b3624c478a:9363546c@us-cdbr-iron-east-02.cleardb.net/heroku_30d5e15761ab000?reconnect=true";
         } 
     URI dbUri = new URI(str);
     
     String username = dbUri.getUserInfo().split(":")[0];
     System.out.println("USERNAME: "+username);
     String password = dbUri.getUserInfo().split(":")[1];
     System.out.println("Password: "+password);
     String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
     System.out.println("DB URL: "+dbUrl);
     Class.forName("com.mysql.jdbc.Driver");
     String springUrl = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/heroku_30d5e15761ab000?reconnect=true";
		DriverManagerDataSource dataSource = new DriverManagerDataSource(springUrl, username, password);
		
		
	 jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	

	

	
	
	
}