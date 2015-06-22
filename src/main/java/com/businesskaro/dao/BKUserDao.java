package com.businesskaro.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.businesskaro.model.BKUser;

@Repository
public class BKUserDao {
	
	@Autowired
	DBConnection  db;

	String insert = "INSERT INTO user (user_name,password_enc,email,phone) VALUES (?, ?, ?, ?)";
	
	String update = "UPDATE user SET user_name = ?, password_enc = ?, email = ?, phone = ? WHERE user_name = ?";
	
	String selectAll = "SELECT user_name, password_enc, email,phone FROM user WHERE user_name = ? ";
	
	public void createUser(BKUser user, char[] password){
		
		Object[] params = new Object[]{
				user.userName, "todo", user.email, user.phoneNo
		};
		
		int result= db.jdbcTemplate.update(insert, params);
		System.out.println("No of rows created :" + result);
	}
	
	public void deleteUser(BKUser user){
		
	}
	
	public void updateUser(BKUser user, char[] password){
		Object[] params = new Object[]{
				user.userName, "todo", user.email, user.phoneNo
		};
		
		int result= db.jdbcTemplate.update(update, params);
		System.out.println("No of rows created :" + result);
	}
	
	public BKUser retrieveBKUser(String userName, char[] password){
		
		List<BKUser> users = db.jdbcTemplate.query(selectAll, new RowMapper<BKUser>(){

			@Override
			public BKUser mapRow(ResultSet rs, int arg1) throws SQLException {
				BKUser user = new BKUser();
				user.email = rs.getString("email");
				user.phoneNo = rs.getString("phone");
				user.userName = rs.getString("user_name");
				
				return user;
			}} );
		
		return users.get(0);
	}
	
	



	
}
