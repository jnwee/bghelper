package com.project24.bghelper.repository;

import com.project24.bghelper.model.Kit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitRepository extends MongoRepository<Kit, String> {
}
