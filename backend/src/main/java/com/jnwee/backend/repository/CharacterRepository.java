package com.jnwee.backend.repository;

import com.jnwee.backend.model.Char;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharacterRepository extends MongoRepository<Char, String> {
    // Ye can add custom query methods here if ye need 'em!
}
