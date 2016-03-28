package com.businesskaro.entity.repo;

//import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.TagEntity;
import com.businesskaro.entity.TblEmailAudit;

public interface TblEmailAuditRepo extends CrudRepository<TblEmailAudit, Integer>{
	List<TblEmailAudit> findAllBySentFrom(Integer sentFrom);
	List<TblEmailAudit> findAllBySentFromAndEmailSentDate(Integer sentFrom,Date emailSentDate);
	
}

