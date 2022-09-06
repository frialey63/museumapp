package org.pjp.museum.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vaadin.jarkjar.questionnaire.model.UserAnswerSet;

@Repository
public interface UserAnswerSetRepository extends MongoRepository<UserAnswerSet, String> {

}
