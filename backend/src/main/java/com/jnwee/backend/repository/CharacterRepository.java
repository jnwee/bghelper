package com.jnwee.backend.repository;

import com.jnwee.backend.model.Char;
import com.jnwee.backend.model.Progress;
import com.jnwee.backend.model.Status;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CharacterRepository extends MongoRepository<Char, String> {
    @Query(
        value = "{ 'progress': ?0 }",
        fields = "{ 'id': 1, 'name': 1, 'status': 1, 'progress': 1 }"
    )
    List<Char> findByProgress(Progress progress, Sort sort);

    @Query(
        value = "{ 'status': ?0 }",
        fields = "{ 'id': 1, 'name': 1, 'status': 1, 'progress': 1 }"
    )
    List<Char> findByStatus(Status status, Sort sort);
}
