package org.pjp.museum.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vaadin.jarkjar.questionnaire.model.QuestionSet;

@Repository
public interface QuestionSetRepository extends MongoRepository<QuestionSet, String> {

}
