package com.businesskaro.entity.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.TblUserPassword;
import com.businesskaro.entity.TblUserPasswordProfile;

public interface TblUserPasswordProfileRepo extends CrudRepository<TblUserPasswordProfile, Integer> {

}
