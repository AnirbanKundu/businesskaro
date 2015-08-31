package com.businesskaro.entity.repo;

import org.springframework.data.repository.CrudRepository;

import com.businesskaro.entity.LkpQuestion;

public interface QuestionRepo extends CrudRepository<LkpQuestion, Integer> {
	public Iterable<LkpQuestion> findByQuestTyp(String questionType);
}
