package com.project24.bghelper.repository;

import com.project24.bghelper.model.Party;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends MongoRepository<Party, String> {
}
