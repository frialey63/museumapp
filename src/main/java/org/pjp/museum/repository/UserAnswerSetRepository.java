package org.pjp.museum.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.addons.pjp.questionnaire.model.UserAnswerSet;

@Repository
public interface UserAnswerSetRepository extends MongoRepository<UserAnswerSet, String> {

    Optional<UserAnswerSet> findByQuestionSetUuidAndSessionId(String questionSetUuid, String sessionId);
}
