package com.businesskaro.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() throws URISyntaxException, SQLException {
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
        return DriverManager.getConnection(dbUrl, username, password);
    }
}