package org.solomon11.repository;

import org.solomon11.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Users extends MongoRepository<User,String> {
    boolean existsByUsername(String username);
}
