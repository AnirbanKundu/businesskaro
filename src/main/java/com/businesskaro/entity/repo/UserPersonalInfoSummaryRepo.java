package com.businesskaro.entity.repo;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.UserPersonalInfoSummary;

@Transactional(value=TxType.MANDATORY)
public interface UserPersonalInfoSummaryRepo extends CrudRepository<UserPersonalInfoSummary, Integer> {

}

