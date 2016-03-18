package com.businesskaro.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.businesskaro.entity.TblUserManagement;
public interface TblUserManagementRepo extends JpaRepository<TblUserManagement, Integer> {
	//public Iterable<TblUserManagement> findAllByTblUserPersInfoSumryAndReqOffrTyp(TblUserManagement findOne,String name);
}
