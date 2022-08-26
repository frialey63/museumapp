package org.pjp.museum.repository;

import java.util.Optional;

import org.pjp.museum.model.Setting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends MongoRepository<Setting, String> {

    Optional<Setting> findByName(String name);
}
