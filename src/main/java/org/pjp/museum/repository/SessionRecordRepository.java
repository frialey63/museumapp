package org.pjp.museum.repository;

import org.pjp.museum.model.SessionRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRecordRepository extends MongoRepository<SessionRecord, String> {

}
