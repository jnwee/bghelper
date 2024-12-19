package com.jnwee.backend.repository;

import com.jnwee.backend.model.Char;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CharacterRepository extends MongoRepository<Char, String> {
    @Query(value = "{}", fields = "{ 'id': 1, 'name': 1, 'dead': 1 }")
    List<Char> findAllLightweight(Sort sort);
}
