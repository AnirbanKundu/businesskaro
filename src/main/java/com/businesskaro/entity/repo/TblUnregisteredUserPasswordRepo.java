package com.businesskaro.entity.repo;

import java.util.List;
import java.lang.String;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.businesskaro.entity.TblUnregisteredUserPassword;


public interface TblUnregisteredUserPasswordRepo extends CrudRepository<TblUnregisteredUserPassword, Integer> {
	List<TblUnregisteredUserPassword> findByUsrName(String usrname);
	// @Query("SELECT p FROM TblUnregisteredUserPassword p WHERE p.usrsalt = usrsalt")
	 //public List<TblUnregisteredUserPassword> find(@Param("usrsalt") String lastName);
	public List<TblUnregisteredUserPassword> findByUsrSalt(String usrsalt);
}
