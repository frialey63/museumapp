package org.pjp.museum.repository;

import org.pjp.museum.model.Exhibit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitRepository extends MongoRepository<Exhibit, String> {

}
