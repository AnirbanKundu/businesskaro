package com.businesskaro.entity.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.businesskaro.entity.TagEntity;
import com.businesskaro.model.AdminEntitySearch;

@Configuration
public class CustomRepository {

	@Autowired
	DataSource datasource;

	public int getData(int value) {
		return value;
	}

	public List<AdminEntitySearch> searchEnityForAdmin(final String entityType, final Integer industryId, final Integer stateId)
			throws Exception {

		List<AdminEntitySearch> adminSearch = new ArrayList<AdminEntitySearch>();
		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
			adminSearch = jdbcTemplate.query("CALL Return_offer_details( ?,?,? )",
					new Object[] { entityType,industryId, stateId}, new RowMapper<AdminEntitySearch>() {

						@Override
						public AdminEntitySearch mapRow(ResultSet rs, int arg1)
								throws SQLException {
							AdminEntitySearch entity = new AdminEntitySearch();
							entity.entityid = rs.getInt("n_req_offr_id");
							entity.entitytitle = rs
									.getString("n_req_offr_title");
							entity.entitydescription = rs
									.getString("n_req_offr_descr");
							entity.usercreatedId = rs.getInt("n_usr_id");
							entity.name = rs.getString("n_fst_name") + " " + rs.getString("n_lst_name");
							System.out.println(entity.toString());
							return entity;
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(
					"Error occurs while executing procedure");
		} finally {
		}
		return adminSearch;
	}
	
	public List<TagEntity> searchTag(String[] tagNames,	String entityType){
		List<TagEntity> tagEntities = null;
		
		return tagEntities;
	}

}
