package com.project24.bghelper.repository;

import com.project24.bghelper.model.Companion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanionRepository extends MongoRepository<Companion, String> {
}
