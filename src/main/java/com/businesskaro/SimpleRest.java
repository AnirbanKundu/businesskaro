package com.businesskaro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businesskaro.dao.BKUserDao;
import com.businesskaro.model.BKUser;

@RestController
@EnableAutoConfiguration
@RequestMapping("/helloworld")
public class SimpleRest {

	@Autowired
	BKUserDao dao;
	
	//the below annotation provides routing information.
	@RequestMapping(method = RequestMethod.GET)
    public String home() {
		BKUser user = new BKUser();
		user.email = "some1";
		user.phoneNo = "wewew1";
		user.userName = "anirba1";
		dao.createUser(user, null);
        return "Hello World!";
    }
}
