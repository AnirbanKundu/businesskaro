package com.businesskaro.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.businesskaro.model.AgeGroup1;
import com.businesskaro.model.Education1;
import com.businesskaro.model.Industry1;


public class LookUpUtilDao {
	
	@Autowired
	DBConnection  db;
	
	String selectAllAge = "SELECT age_id, displ_age FROM lkp_age_grp";
	String selectAllEducation = "SELECT EDUCTN_ID, EDUCTN_NAME FROM lkp_education";
	String selectAllIndustry = "SELECT INDUSTRY_ID, INDUSTRY_NAME FROM lkp_industry";
	
	public List<AgeGroup1> getAgeGroups(){		
		List<AgeGroup1> ageGroups = db.jdbcTemplate.query(selectAllAge, new RowMapper<AgeGroup1>(){
			@Override
			public AgeGroup1 mapRow(ResultSet rs, int arg1) throws SQLException {
				AgeGroup1 age = new AgeGroup1();
				age.age_id = rs.getInt("age_id");
				age.displ_age = rs.getString("displ_age");
				return age;
			}} );		
		return ageGroups;
	}
	public List<Education1> getAllEducation(){		
		List<Education1> educations = db.jdbcTemplate.query(selectAllEducation, new RowMapper<Education1>(){
			@Override
			public Education1 mapRow(ResultSet rs, int arg1) throws SQLException {
				Education1 education = new Education1();
				education.education_id = rs.getInt("EDUCTN_ID");
				education.education_name = rs.getString("EDUCTN_NAME");
				return education;
			}} );		
		return educations;
	}	
	
	public List<Industry1> getAllIndustry(){		
		List<Industry1> educations = db.jdbcTemplate.query(selectAllIndustry, new RowMapper<Industry1>(){
			@Override
			public Industry1 mapRow(ResultSet rs, int arg1) throws SQLException {
				Industry1 industry = new Industry1();
				industry.industry_id = rs.getInt("INDUSTRY_ID");
				industry.industry_name = rs.getString("INDUSTRY_NAME");
				return industry;
			}} );		
		return educations;
	}
}

