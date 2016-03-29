package com.businesskaro.entity.repo;

//import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.businesskaro.entity.TblEmailAudit;

public interface TblEmailAuditRepo extends JpaRepository<TblEmailAudit, Integer>{
	List<TblEmailAudit> findAllBySentFrom(Integer sentFrom);
	List<TblEmailAudit> findAllBySentFromAndEmailSentDate(Integer sentFrom,Date emailSentDate);
	
}
