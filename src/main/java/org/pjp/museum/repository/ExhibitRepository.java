package org.pjp.museum.repository;

import java.util.Optional;

import org.pjp.museum.model.Exhibit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitRepository extends MongoRepository<Exhibit, String> {

    Optional<Exhibit> findByTailNumber(String tailNumber);
}
