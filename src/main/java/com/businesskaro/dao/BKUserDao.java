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

	String insert = "INSERT INTO tbl_user_password ( usr_name, usr_password, usr_email, usr_salt) VALUES ( ?, ?, ?,?)";
	
	String update = "UPDATE tbl_user_password SET usr_name = ?, usr_password = ?, usr_email = ? WHERE usr_name = ?";
	
	String selectAll = "SELECT usr_id, usr_name, usr_password, usr_email, usr_salt FROM tbl_user_password WHERE usr_name = ? ";
	
	String selectAllById = "SELECT usr_id, usr_name, usr_password, usr_email, usr_salt FROM tbl_user_password WHERE usr_id = ? ";
	
	public void createUser(BKUser user ){
		
		Object[] params = new Object[]{
				user.userName, user.password, user.email,  user.randomSalt
		};
		
		int result= db.jdbcTemplate.update(insert, params);
		System.out.println("No of rows created :" + result);
	}
	
	public void deleteUser(BKUser user){
		
	}
	
	public void updateUser(BKUser user){
		Object[] params = new Object[]{
				user.id, user.userName, user.password, user.email, user.phoneNo, user.userName
		};
		
		int result= db.jdbcTemplate.update(update, params);
		System.out.println("No of rows created :" + result);
	}
	
	public BKUser retrieveBKUser(String userName){
		
		Object[] params = new Object[]{ userName };
		
		List<BKUser> users = db.jdbcTemplate.query(selectAll, params, new RowMapper<BKUser>(){

			@Override
			public BKUser mapRow(ResultSet rs, int arg1) throws SQLException {
				BKUser user = new BKUser();
				user.id = rs.getInt("usr_id");
				user.email = rs.getString("usr_email");
				user.userName = rs.getString("usr_name");
				user.password = rs.getString("usr_password");
				user.randomSalt = rs.getString("usr_salt");
				return user;
			}} );
		
		return users.get(0);
	}
	
	public BKUser retrieveBKId(Integer userId){
		
		Object[] params = new Object[]{ userId };
		
		List<BKUser> users = db.jdbcTemplate.query(selectAllById, params, new RowMapper<BKUser>(){

			@Override
			public BKUser mapRow(ResultSet rs, int arg1) throws SQLException {
				BKUser user = new BKUser();
				user.id = rs.getInt("usr_id");
				user.email = rs.getString("usr_email");
				user.userName = rs.getString("usr_name");
				user.password = rs.getString("usr_password");
				user.randomSalt = rs.getString("usr_salt");
				return user;
			}} );
		
		return users.get(0);
	}
	

	
}
