package com.businesskaro.entity.repo;


import java.util.List;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.TblPolicy;

public interface TblPolicyRepo extends CrudRepository<TblPolicy, Integer>{
	//List<TblPolicy> findAllBySentFrom(Integer sentFrom);
	List<TblPolicy> findAllByUsrId(Integer usrID);
	

}
