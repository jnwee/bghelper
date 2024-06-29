package com.project24.bghelper.repository;

import com.project24.bghelper.model.Companion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanionRepository extends MongoRepository<Companion, String> {
}
