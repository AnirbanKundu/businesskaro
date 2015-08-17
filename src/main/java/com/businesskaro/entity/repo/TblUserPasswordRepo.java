package com.businesskaro.entity.repo;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.TblUserPassword;
import java.lang.String;
import java.util.List;

public interface TblUserPasswordRepo extends CrudRepository<TblUserPassword, Integer> {

	List<TblUserPassword> findByUsrName(String usrname);
}
