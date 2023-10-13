package org.pjp.museum.repository;

import java.util.List;

import org.pjp.museum.model.SessionRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRecordRepository extends MongoRepository<SessionRecord, String> {

	List<SessionRecord> findByOrderByStartTime();
}
