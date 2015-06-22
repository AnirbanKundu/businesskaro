package com.businesskaro.security;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.ws.BindingType;

public class BKGuid {
	 private static String SEEDS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    
	 public static String getNextGuid(){
	    	StringBuilder builder = new StringBuilder();
	    	Random random  = new Random();
	    	for(int i =0 ;i < 24; i ++){
	    		int r = random.nextInt(SEEDS.length());
	    		builder.append(SEEDS.charAt(r));
	    	}
	    	return builder.toString();
	 }
	 
	 public static boolean isValidGUID(String guid){
		 Pattern pattern = Pattern.compile("(["+SEEDS+"]){24}");
		 Matcher m = pattern.matcher(guid);
		 return m.matches();
	 }
	 
}

